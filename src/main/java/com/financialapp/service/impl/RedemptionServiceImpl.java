package com.financialapp.service.impl;

import com.financialapp.dto.RedemptionDTO;
import com.financialapp.model.RewardCatalog;
import com.financialapp.model.Redemption;
import com.financialapp.model.RedemptionStatus;
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

        RedemptionStatus status = dto.getStatus() != null ? dto.getStatus() : RedemptionStatus.PENDING;

        // Points check + deduct only if creating directly with SUCCESS status
        if (status == RedemptionStatus.SUCCESS) {
            if (user.getPoints() < catalogItem.getPointsRequired()) {
                throw new IllegalStateException("Insufficient points to redeem this item.");
            }
            user.setPoints(user.getPoints() - catalogItem.getPointsRequired());
            userRepository.save(user);
        }

        Redemption redemption = new Redemption();
        redemption.setUser(user);
        redemption.setCatalogItem(catalogItem);
        redemption.setRedeemedAt(dto.getRedeemedAt() != null ? dto.getRedeemedAt() :
                (status == RedemptionStatus.SUCCESS ? LocalDateTime.now() : null));
        redemption.setStatus(status);
        redemption.setFulfillmentDetails(dto.getFulfillmentDetails());
        redemption.setFailureReason(dto.getFailureReason());
        redemption.setExpiryDate(dto.getExpiryDate());
        redemption.setRedemptionCode(dto.getRedemptionCode() != null ? dto.getRedemptionCode() : generateRedemptionCode());

        Redemption saved = redemptionRepository.save(redemption);

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
                    if (r.getCatalogItem() != null) {
                        dto.setCatalogItemId(r.getCatalogItem().getCatalogItemId());
                    }
                    return dto;
                })
                .toList();
    }
    
    @Override
    public RedemptionDTO getRedemptionById(Integer id) {
        Redemption saved = redemptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Redemption not found"));
        RedemptionDTO out = modelMapper.map(saved, RedemptionDTO.class);
        out.setUserId(saved.getUser().getUserId());
        if (saved.getCatalogItem() != null) {
            out.setCatalogItemId(saved.getCatalogItem().getCatalogItemId());
        }
        return out;
    }




    @Override
    @Transactional
    public RedemptionDTO updateRedemption(Integer id, RedemptionDTO dto) {
        Redemption existing = redemptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Redemption not found"));

        User user = existing.getUser();
        RewardCatalog oldCatalog = existing.getCatalogItem();
        int oldCost = oldCatalog != null ? oldCatalog.getPointsRequired() : 0;

        RewardCatalog newCatalog = oldCatalog;
        if (dto.getCatalogItemId() != null) {
            newCatalog = catalogRepository.findByCatalogItemIdAndActiveTrue(dto.getCatalogItemId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Catalog item is inactive or does not exist: " + dto.getCatalogItemId()));
        }
        int newCost = newCatalog != null ? newCatalog.getPointsRequired() : 0;

        RedemptionStatus oldStatus = existing.getStatus();
        RedemptionStatus newStatus = dto.getStatus() != null ? dto.getStatus() : oldStatus;

        boolean oldWasRefunded = oldStatus == RedemptionStatus.FAILED || oldStatus == RedemptionStatus.REFUNDED;
        boolean newIsRefunded = newStatus == RedemptionStatus.FAILED || newStatus == RedemptionStatus.REFUNDED;

        // Catalog change logic
        if (newCatalog != oldCatalog) {
            if (oldCost > 0 && oldStatus == RedemptionStatus.SUCCESS) {
                user.setPoints(user.getPoints() + oldCost);
            }
            if (newCost > 0 && newStatus == RedemptionStatus.SUCCESS) {
                if (user.getPoints() < newCost) {
                    throw new IllegalStateException("Insufficient points to switch to new catalog item.");
                }
                user.setPoints(user.getPoints() - newCost);
            }
            existing.setCatalogItem(newCatalog);
        }

        // Status change logic
        if (oldStatus == RedemptionStatus.PENDING && newStatus == RedemptionStatus.SUCCESS) {
            if (user.getPoints() < newCost) {
                throw new IllegalStateException("Insufficient points to complete redemption.");
            }
            user.setPoints(user.getPoints() - newCost);
        }

        if (oldStatus == RedemptionStatus.SUCCESS && newIsRefunded) {
            user.setPoints(user.getPoints() + newCost);
        }

        if (oldWasRefunded && newStatus == RedemptionStatus.SUCCESS) {
            if (user.getPoints() < newCost) {
                throw new IllegalStateException("Insufficient points to re-apply redemption.");
            }
            user.setPoints(user.getPoints() - newCost);
        }

        userRepository.save(user);

        // Update redemption
        existing.setStatus(newStatus);
        existing.setFulfillmentDetails(dto.getFulfillmentDetails());
        existing.setFailureReason(dto.getFailureReason());
        existing.setExpiryDate(dto.getExpiryDate());
        if (dto.getRedemptionCode() != null && !dto.getRedemptionCode().isBlank()) {
            existing.setRedemptionCode(dto.getRedemptionCode());
        } else if (existing.getRedemptionCode() == null) {
            existing.setRedemptionCode(generateRedemptionCode());
        }
        if (dto.getRedeemedAt() != null) {
            existing.setRedeemedAt(dto.getRedeemedAt());
        } else if (existing.getRedeemedAt() == null && newStatus == RedemptionStatus.SUCCESS) {
            existing.setRedeemedAt(LocalDateTime.now());
        }
        if (dto.getUserId() != null && !dto.getUserId().equals(user.getUserId())) {
            User newUser = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            existing.setUser(newUser);
        }

        Redemption saved = redemptionRepository.save(existing);
        RedemptionDTO out = modelMapper.map(saved, RedemptionDTO.class);
        out.setUserId(saved.getUser().getUserId());
        if (saved.getCatalogItem() != null) {
            out.setCatalogItemId(saved.getCatalogItem().getCatalogItemId());
        }
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
