package com.financialapp.service.impl;

import com.financialapp.dto.RewardDTO;
import com.financialapp.model.*;
import com.financialapp.repository.*;
import com.financialapp.service.RewardService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FinancialGoalRepository goalRepository;

    @Autowired
    private FinancialActivityRepository activityRepository;

    @Autowired
    private RewardCatalogRepository catalogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RewardDTO createReward(RewardDTO dto) {
        // Validation: Exactly one of goalId or activityId must be provided
        boolean hasGoalId = dto.getGoalId() != null;
        boolean hasActivityId = dto.getActivityId() != null;

        if (!(hasGoalId ^ hasActivityId)) { // XOR: only one should be provided
            throw new IllegalArgumentException("Exactly one of goalId or activityId must be provided.");
        }

        Reward reward = modelMapper.map(dto, Reward.class);
        reward.setUser(userRepository.findById(dto.getUserId()).orElseThrow());

        if (hasGoalId) {
            reward.setGoal(goalRepository.findById(dto.getGoalId()).orElseThrow(() ->
                    new IllegalArgumentException("Invalid goalId: " + dto.getGoalId())));
        } else {
            reward.setGoal(null);
        }

        if (hasActivityId) {
            reward.setActivity(activityRepository.findById(dto.getActivityId()).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activityId: " + dto.getActivityId())));
        } else {
            reward.setActivity(null);
        }

        reward.setCatalogItem(catalogRepository.findById(dto.getCatalogItemId()).orElse(null));
        return modelMapper.map(rewardRepository.save(reward), RewardDTO.class);
    }

    @Override
    public RewardDTO getRewardById(Long id) {
        Reward reward = rewardRepository.findById(id).orElseThrow();
        return modelMapper.map(reward, RewardDTO.class);
    }

    @Override
    public List<RewardDTO> getAllRewards() {
        return rewardRepository.findAll()
                .stream()
                .map(reward -> modelMapper.map(reward, RewardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RewardDTO updateReward(Long id, RewardDTO dto) {
        boolean hasGoalId = dto.getGoalId() != null;
        boolean hasActivityId = dto.getActivityId() != null;

        if (!(hasGoalId ^ hasActivityId)) {
            throw new IllegalArgumentException("Exactly one of goalId or activityId must be provided.");
        }

        Reward reward = rewardRepository.findById(id).orElseThrow();

        reward.setPoints(dto.getPoints());
        reward.setEarnedAt(dto.getEarnedAt());
        reward.setUser(userRepository.findById(dto.getUserId()).orElseThrow());

        if (hasGoalId) {
            reward.setGoal(goalRepository.findById(dto.getGoalId()).orElseThrow(() ->
                    new IllegalArgumentException("Invalid goalId: " + dto.getGoalId())));
            reward.setActivity(null);
        } else {
            reward.setActivity(activityRepository.findById(dto.getActivityId()).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activityId: " + dto.getActivityId())));
            reward.setGoal(null);
        }

        reward.setCatalogItem(catalogRepository.findById(dto.getCatalogItemId()).orElse(null));
        return modelMapper.map(rewardRepository.save(reward), RewardDTO.class);
    }

    @Override
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }
}
