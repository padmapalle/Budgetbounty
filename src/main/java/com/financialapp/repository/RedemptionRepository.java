package com.financialapp.repository;

import com.financialapp.model.Redemption;
import com.financialapp.model.RedemptionStatus;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RedemptionRepository extends JpaRepository<Redemption, Integer> {
	
	List<Redemption> findAllByStatusAndExpiryDateBefore(RedemptionStatus status, LocalDateTime dateTime);

}
