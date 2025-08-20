package com.financialapp.repository;

<<<<<<< HEAD
=======
import com.financialapp.model.Reward;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import com.financialapp.entity.Reward;

=======
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
public interface RewardRepository extends JpaRepository<Reward, Long> {

	List<Reward> findByUser_UserId(Integer userId);
}
