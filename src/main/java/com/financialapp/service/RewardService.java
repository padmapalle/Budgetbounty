package com.financialapp.service;

import com.financialapp.dto.RewardDTO;
import java.util.List;

public interface RewardService {
    RewardDTO createReward(RewardDTO dto);
    RewardDTO getRewardById(Long id);
    List<RewardDTO> getAllRewards();
    RewardDTO updateReward(Long id, RewardDTO dto);
    void deleteReward(Long id);
}
