package com.financialapp.service.impl;

import com.financialapp.dto.RedemptionDTO;
import com.financialapp.model.Redemption;
import com.financialapp.model.User;
import com.financialapp.model.RewardCatalog;
import com.financialapp.repository.*;
import com.financialapp.service.RedemptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public RedemptionDTO createRedemption(RedemptionDTO dto) {
        Redemption redemption = modelMapper.map(dto, Redemption.class);
        redemption.setUser(userRepository.findById(dto.getUserId()).orElseThrow());
        redemption.setCatalogItem(catalogRepository.findById(dto.getCatalogItemId()).orElseThrow());
        return modelMapper.map(redemptionRepository.save(redemption), RedemptionDTO.class);
    }

    @Override
    public RedemptionDTO getRedemptionById(Integer id) {
        Redemption redemption = redemptionRepository.findById(id).orElseThrow();
        return modelMapper.map(redemption, RedemptionDTO.class);
    }

    @Override
    public List<RedemptionDTO> getAllRedemptions() {
        return redemptionRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RedemptionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RedemptionDTO updateRedemption(Integer id, RedemptionDTO dto) {
        Redemption redemption = redemptionRepository.findById(id).orElseThrow();
        redemption.setRedeemedAt(dto.getRedeemedAt());
        redemption.setStatus(dto.getStatus());
        redemption.setFulfillmentDetails(dto.getFulfillmentDetails());
        redemption.setFailureReason(dto.getFailureReason());
        redemption.setExpiryDate(dto.getExpiryDate());
        redemption.setRedemptionCode(dto.getRedemptionCode());
        redemption.setUser(userRepository.findById(dto.getUserId()).orElseThrow());
        redemption.setCatalogItem(catalogRepository.findById(dto.getCatalogItemId()).orElseThrow());
        return modelMapper.map(redemptionRepository.save(redemption), RedemptionDTO.class);
    }

    @Override
    public void deleteRedemption(Integer id) {
        redemptionRepository.deleteById(id);
    }
}
