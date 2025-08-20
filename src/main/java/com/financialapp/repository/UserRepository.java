package com.financialapp.repository;

<<<<<<< HEAD
import java.util.Optional;

=======
import com.financialapp.model.User;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional;

import com.financialapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    @Query("SELECT COALESCE(u.points, 0) FROM User u WHERE u.userId = :userId")
    int getCurrentPoints(@Param("userId") int userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.points = :newPoints WHERE u.userId = :userId")
    void updatePoints(@Param("userId") int userId, @Param("newPoints") int points);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE User u SET u.points = u.points + :delta WHERE u.userId = :userId")
    void addPoints(@Param("userId") int userId, @Param("delta") int pointsToAdd);

    Optional<User> findByEmail(String email);
}
=======
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Get current points (NULL → 0)
    @Query("SELECT COALESCE(u.points, 0) FROM User u WHERE u.userId = :userId")
    int getCurrentPoints(@Param("userId") int userId);

    // Atomic set
    @Modifying
    @Query("UPDATE User u SET u.points = :newPoints WHERE u.userId = :userId")
    void updatePoints(@Param("userId") int userId, @Param("newPoints") int points);

    // Atomic increment (optional but useful)
    @Modifying
    @Query("UPDATE User u SET u.points = u.points + :delta WHERE u.userId = :userId")
    void addPoints(@Param("userId") int userId, @Param("delta") int pointsToAdd);
}
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
