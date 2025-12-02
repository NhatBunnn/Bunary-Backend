package com.bunary.vocab.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bunary.vocab.user.model.UserStats;

@Repository
public interface UserStatsRepo extends JpaRepository<UserStats, Long> {
    Optional<UserStats> findByUserId(UUID userId);

}
