package com.smartStudy.Planner.repositories;

import com.smartStudy.Planner.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(@NotBlank(message = "User Name cannot be empty.") @Size(min = 3, max = 20, message = "User Name must be in between 3 to 20 characters.") String userName);
}
