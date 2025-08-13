package com.financialapp.repository;

import com.financialapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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