package com.financialapp.repository;

import com.financialapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("UPDATE User u SET u.points = :points WHERE u.userId = :userId")
    void updatePointsByUserId(Integer userId, Integer points);
}
