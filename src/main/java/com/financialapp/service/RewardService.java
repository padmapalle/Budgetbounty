package com.financialapp.service;

import com.financialapp.dto.RewardDTO;
<<<<<<< HEAD
import com.financialapp.entity.FinancialActivity;
import com.financialapp.entity.FinancialGoal;
import com.financialapp.entity.Reward;
=======
import com.financialapp.model.FinancialActivity;
import com.financialapp.model.FinancialGoal;
import com.financialapp.model.Reward;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4

import java.util.List;

public interface RewardService {
    RewardDTO createReward(RewardDTO dto);
    RewardDTO getRewardById(Long id);
    List<RewardDTO> getAllRewards();
    RewardDTO updateReward(Long id, RewardDTO dto);
    void deleteReward(Long id);
    Reward createRewardForActivity(FinancialActivity activity, int points);
    Reward createRewardForGoal(FinancialGoal goal, int points);

    List<RewardDTO> getRewardsByUserId(Integer userId);
}
