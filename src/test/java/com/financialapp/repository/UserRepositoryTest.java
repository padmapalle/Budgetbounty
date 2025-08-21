package com.financialapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.financialapp.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
<<<<<<< HEAD
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
=======
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // ✅ Don't replace with H2
@ActiveProfiles("test") // ✅ Forces use of application-test.properties
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    @Transactional
<<<<<<< HEAD
    void getCurrentPoints_ShouldReturnZero_WhenPointsZero() {
        User user = new User();
        user.setEmail("zeropoints@example.com");
        user.setPoints(0); // Changed from null to 0
        userRepo.save(user);

        int points = userRepo.getCurrentPoints(user.getUserId());
=======
    void getCurrentPoints_ShouldReturnZero_WhenPointsNull() {
        User user = new User();
        user.setEmail("nullpoints@example.com");
        user.setPoints(null);
        userRepo.save(user);

        int points = userRepo.getCurrentPoints(user.getUserId());

>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
        assertThat(points).isEqualTo(0);
    }

    @Test
    @Transactional
    void updatePoints_ShouldUpdatePoints() {
        User user = new User();
        user.setEmail("update@example.com");
        user.setPoints(100);
        userRepo.save(user);

        userRepo.updatePoints(user.getUserId(), 300);

        int points = userRepo.getCurrentPoints(user.getUserId());
        assertThat(points).isEqualTo(300);
    }

    @Test
    @Transactional
    void addPoints_ShouldIncrementPoints() {
        User user = new User();
        user.setEmail("increment@example.com");
        user.setPoints(100);
        userRepo.save(user);

        userRepo.addPoints(user.getUserId(), 50);

        int points = userRepo.getCurrentPoints(user.getUserId());
        assertThat(points).isEqualTo(150);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
