package com.bunary.vocab.learning.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunary.vocab.learning.model.UserWordSetDaily;

public interface UserWsDailyRepo extends JpaRepository<UserWordSetDaily, Long> {
    @Query(value = """
            SELECT *
            FROM user_wordset_daily
            WHERE user_id = :userId
            ORDER BY created_at DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<UserWordSetDaily> findLatest(UUID userId);
}
