package com.financialapp.service.impl;

import com.financialapp.dto.RedemptionDTO;
import com.financialapp.model.RewardCatalog;
import com.financialapp.model.Redemption;
import com.financialapp.model.User;
import com.financialapp.repository.RedemptionRepository;
import com.financialapp.repository.RewardCatalogRepository;
import com.financialapp.repository.UserRepository;
import com.financialapp.service.RedemptionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RedemptionServiceImpl implements RedemptionService {

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RewardCatalogRepository catalogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public RedemptionDTO createRedemption(RedemptionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        RewardCatalog catalogItem = catalogRepository.findByCatalogItemIdAndActiveTrue(dto.getCatalogItemId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Catalog item is inactive or does not exist: " + dto.getCatalogItemId()));

        // Points check
        if (user.getPoints() < catalogItem.getPointsRequired()) {
            throw new IllegalStateException("Insufficient points to redeem this item.");
        }

        // Deduct points
        user.setPoints(user.getPoints() - catalogItem.getPointsRequired());
        userRepository.save(user);

        // Create redemption
        Redemption redemption = new Redemption();
        redemption.setUser(user);
        redemption.setCatalogItem(catalogItem);
        redemption.setRedeemedAt(dto.getRedeemedAt() != null ? dto.getRedeemedAt() : LocalDateTime.now());
        redemption.setStatus(dto.getStatus() != null ? dto.getStatus() : "PENDING");
        redemption.setFulfillmentDetails(dto.getFulfillmentDetails());
        redemption.setFailureReason(dto.getFailureReason());
        redemption.setExpiryDate(dto.getExpiryDate());
        redemption.setRedemptionCode(dto.getRedemptionCode() != null ? dto.getRedemptionCode() : generateRedemptionCode());

        Redemption saved = redemptionRepository.save(redemption);

        RedemptionDTO out = modelMapper.map(saved, RedemptionDTO.class);
        // ensure DTO contains ids for nested objects
        out.setUserId(saved.getUser().getUserId());
        if (saved.getCatalogItem() != null) out.setCatalogItemId(saved.getCatalogItem().getCatalogItemId());
        return out;
    }

    @Override
    public RedemptionDTO getRedemptionById(Integer id) {
        Redemption saved = redemptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Redemption not found"));
        RedemptionDTO out = modelMapper.map(saved, RedemptionDTO.class);
        out.setUserId(saved.getUser().getUserId());
        if (saved.getCatalogItem() != null) out.setCatalogItemId(saved.getCatalogItem().getCatalogItemId());
        return out;
    }

    @Override
    public List<RedemptionDTO> getAllRedemptions() {
        return redemptionRepository.findAll()
                .stream()
                .map(r -> {
                    RedemptionDTO dto = modelMapper.map(r, RedemptionDTO.class);
                    dto.setUserId(r.getUser().getUserId());
                    if (r.getCatalogItem() != null) dto.setCatalogItemId(r.getCatalogItem().getCatalogItemId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Update redemption with safe point handling.
     * - Handles catalog change (refund old points, deduct new points)
     * - Handles status transitions (refund on FAILED/REFUNDED, re-deduct otherwise)
     */
    @Override
    @Transactional
    public RedemptionDTO updateRedemption(Integer id, RedemptionDTO dto) {
        Redemption existing = redemptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Redemption not found"));

        User user = existing.getUser();
        RewardCatalog oldCatalog = existing.getCatalogItem();
        int oldCost = oldCatalog != null ? oldCatalog.getPointsRequired() : 0;

        // Determine new catalog item (could be same)
        RewardCatalog newCatalog = oldCatalog;
        if (dto.getCatalogItemId() != null) {
            newCatalog = catalogRepository.findByCatalogItemIdAndActiveTrue(dto.getCatalogItemId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Catalog item is inactive or does not exist: " + dto.getCatalogItemId()));
        }
        int newCost = newCatalog != null ? newCatalog.getPointsRequired() : 0;

        String oldStatus = existing.getStatus() != null ? existing.getStatus().toUpperCase() : null;
        String newStatus = dto.getStatus() != null ? dto.getStatus().toUpperCase() : oldStatus;

        // 1) Handle catalog change -> refund old and deduct new (net)
        if (newCatalog != oldCatalog) {
            // Refund old cost (if any)
            if (oldCost > 0) {
                user.setPoints(user.getPoints() + oldCost);
            }
            // Attempt to deduct new cost
            if (newCost > 0) {
                if (user.getPoints() < newCost) {
                    throw new IllegalStateException("Insufficient points to switch to new catalog item.");
                }
                user.setPoints(user.getPoints() - newCost);
            }
            existing.setCatalogItem(newCatalog);
        }

        // 2) Handle status transitions and refunds
        boolean oldWasRefunded = oldStatus != null && (oldStatus.equals("FAILED") || oldStatus.equals("REFUNDED"));
        boolean newIsRefunded = newStatus != null && (newStatus.equals("FAILED") || newStatus.equals("REFUNDED"));

        // If moving to refunded/failed from a non-refunded state -> refund points (points were deducted at creation)
        if (!oldWasRefunded && newIsRefunded) {
            // refund only if not already refunded via catalog-change logic above
            if (newCost > 0) {
                user.setPoints(user.getPoints() + newCost);
            }
        }

        // If moving from refunded/failed to non-refunded -> re-deduct points
        if (oldWasRefunded && !newIsRefunded) {
            if (newCost > 0) {
                if (user.getPoints() < newCost) {
                    throw new IllegalStateException("Insufficient points to re-apply redemption after status change.");
                }
                user.setPoints(user.getPoints() - newCost);
            }
        }

        // Apply user save (points changes)
        userRepository.save(user);

        // Update redemption fields
        if (dto.getRedeemedAt() != null) existing.setRedeemedAt(dto.getRedeemedAt());
        else if (existing.getRedeemedAt() == null && "SUCCESS".equalsIgnoreCase(newStatus)) {
            existing.setRedeemedAt(LocalDateTime.now());
        }

        existing.setStatus(newStatus);
        existing.setFulfillmentDetails(dto.getFulfillmentDetails());
        existing.setFailureReason(dto.getFailureReason());
        existing.setExpiryDate(dto.getExpiryDate());

        // Update or generate redemption code
        if (dto.getRedemptionCode() != null && !dto.getRedemptionCode().isBlank()) {
            existing.setRedemptionCode(dto.getRedemptionCode());
        } else if (existing.getRedemptionCode() == null) {
            existing.setRedemptionCode(generateRedemptionCode());
        }

        // allow changing user if provided (rare)
        if (dto.getUserId() != null && !dto.getUserId().equals(user.getUserId())) {
            User newUser = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            existing.setUser(newUser);
        }

        Redemption saved = redemptionRepository.save(existing);

        RedemptionDTO out = modelMapper.map(saved, RedemptionDTO.class);
        out.setUserId(saved.getUser().getUserId());
        if (saved.getCatalogItem() != null) out.setCatalogItemId(saved.getCatalogItem().getCatalogItemId());
        return out;
    }

    @Override
    public void deleteRedemption(Integer id) {
        redemptionRepository.deleteById(id);
    }

    // small branded code generator
    private String generateRedemptionCode() {
        return "BB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
