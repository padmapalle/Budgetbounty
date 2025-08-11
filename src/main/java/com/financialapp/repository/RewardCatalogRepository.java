package com.financialapp.repository;

import com.financialapp.model.RewardCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RewardCatalogRepository extends JpaRepository<RewardCatalog, Integer> {

	Optional<RewardCatalog> findByCatalogItemIdAndActiveTrue(Integer catalogItemId);

}
