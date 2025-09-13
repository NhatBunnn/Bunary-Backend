package com.bunary.vocab.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.User;

// Interface Segregation Principle (OK) 
public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);

    User findByEmail(String email);

    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.isEmailVerified = true")
    List<User> findAllVerifiedUsers(@Param("myId") UUID myId);

    Optional<User> findById(UUID id);

    boolean existsByEmail(String email);

    long deleteByIsEmailVerified(boolean isVerifed);
}
