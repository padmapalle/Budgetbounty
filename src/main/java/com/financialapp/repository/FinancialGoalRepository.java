package com.financialapp.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import com.financialapp.entity.FinancialGoal;

=======
import com.financialapp.model.FinancialGoal;
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
public interface FinancialGoalRepository extends JpaRepository<FinancialGoal, Integer> {
}
