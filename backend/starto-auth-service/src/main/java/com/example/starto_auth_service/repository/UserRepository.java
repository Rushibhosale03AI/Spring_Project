package com.example.starto_auth_service.repository;

import com.example.starto_auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA will auto-create this method based on the name
    // We need this to find a user by their email when they log in
    Optional<User> findByEmail(String email);
}