package com.financialapp.repository;

<<<<<<< HEAD
=======
import com.financialapp.model.Redemption;
import com.financialapp.model.RedemptionStatus;

>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

<<<<<<< HEAD
import com.financialapp.entity.Redemption;
import com.financialapp.entity.RedemptionStatus;

=======
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
public interface RedemptionRepository extends JpaRepository<Redemption, Integer> {
	
	List<Redemption> findAllByStatusAndExpiryDateBefore(RedemptionStatus status, LocalDateTime dateTime);

}
