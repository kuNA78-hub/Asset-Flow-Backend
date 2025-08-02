package com.example.demo.proj.repository;

import com.example.demo.proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import Optional

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Method to find a user by their email address
    Optional<User> findByEmail(String email);
}