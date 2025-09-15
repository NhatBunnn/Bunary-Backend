package com.bunary.vocab.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.User;

// Interface Segregation Principle (OK) 
public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);

    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.isEmailVerified = true")
    Page<User> findAllVerifiedUsers(Pageable pageable, @Param("myId") UUID myId);

    Optional<User> findById(UUID id);

    boolean existsByEmail(String email);

    long deleteByIsEmailVerified(boolean isVerifed);
}
