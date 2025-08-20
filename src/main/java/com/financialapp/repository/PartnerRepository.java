package com.financialapp.repository;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financialapp.entity.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {

	Optional<Partner> findByName(String string);

	Optional<Partner> findById(long l);


=======
import com.financialapp.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
}
