package com.financialapp.repository;

import com.financialapp.model.Reward;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {

	List<Reward> findByUser_UserId(Integer userId);
}
