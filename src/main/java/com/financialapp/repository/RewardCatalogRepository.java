package com.financialapp.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import com.financialapp.entity.RewardCatalog;

=======
import com.financialapp.model.RewardCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
import java.util.Optional;

public interface RewardCatalogRepository extends JpaRepository<RewardCatalog, Integer> {

	Optional<RewardCatalog> findByCatalogItemIdAndActiveTrue(Integer catalogItemId);

}
