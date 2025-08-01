package com.financialapp.repository;

import com.financialapp.model.FinancialActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialActivityRepository extends JpaRepository<FinancialActivity, Integer> {
}
