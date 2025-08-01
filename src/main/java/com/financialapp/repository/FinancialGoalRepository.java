package com.financialapp.repository;

import com.financialapp.model.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Integer> {
}
