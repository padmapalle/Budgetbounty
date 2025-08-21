package com.financialapp.repository;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financialapp.entity.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {

	Optional<Partner> findByName(String string);

	Optional<Partner> findById(long l);


<<<<<<< HEAD
=======
import com.financialapp.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
}
