package com.financialapp.repository;

import com.financialapp.model.Redemption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedemptionRepository extends JpaRepository<Redemption, Integer> {
}
