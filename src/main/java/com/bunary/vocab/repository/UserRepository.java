package com.bunary.vocab.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.User;
import com.bunary.vocab.model.enums.AuthProviderEnum;

// Interface Segregation Principle (OK) 
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    User save(User user);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.isEmailVerified = true
            AND u.id != :currUserId
            AND u NOT IN (
                SELECT f.followee
                FROM Follow f
                WHERE f.follower.id = :currUserId
            )
            """)
    Page<User> findAll(UUID currUserId, Pageable pageable);

    @Query("""
            SELECT COUNT(u)
            FROM User u
            WHERE u.isEmailVerified = true
            AND u.id != :currUserId
            AND u NOT IN (
                SELECT f.followee
                FROM Follow f
                WHERE f.follower.id = :currUserId
            )
            """)
    long countForSuggestion(UUID currUserId);

    @Query("SELECT u FROM User u WHERE u.isEmailVerified = true")
    Page<User> findAllVerifiedUsers(Pageable pageable, @Param("myId") UUID myId);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE CONCAT('%', :keyword, '%')")
    Page<User> search(@Param("keyword") String keyword, Pageable pageable);

    Optional<User> findById(UUID id);

    boolean existsByEmail(String email);

    long deleteByIsEmailVerified(boolean isVerifed);

    User findByEmail(String email);

    @EntityGraph(attributePaths = { "roles", "roles.permissions" })
    @Query("SELECT u FROM User u WHERE u.email = :email ")
    Optional<User> findByEmailJoinRolesAndPers(@Param("email") String email);

    @EntityGraph(attributePaths = { "roles", "roles.permissions" })
    @Query("SELECT u FROM User u WHERE u.id = :id ")
    Optional<User> findByIdJoinRolesAndPers(@Param("id") UUID userId);

    Optional<User> findByProviderAndProviderId(AuthProviderEnum provider, String providerId);

    @Query("""
            SELECT u
            FROM User u
            WHERE u.username = :username
            AND u.isEmailVerified = true
            """)
    Optional<User> findByUsername(String username);

}
