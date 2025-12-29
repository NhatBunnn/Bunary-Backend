package com.bunary.vocab.learning.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.user.model.UserStatDaily;

public interface UserStatDailyRepo extends JpaRepository<UserStatDaily, Long> {
    @Query(value = """
            SELECT *
            FROM user_stat_daily
            WHERE user_id = :userId
            ORDER BY created_at DESC
            LIMIT 1
            """, nativeQuery = true)
    Optional<UserStatDaily> findLatest(UUID userId);

    @Query("""
                SELECT u
                FROM UserStatDaily u
                WHERE u.user.id = :userId
                    AND u.createdAt >= :start
                    AND u.createdAt < :end
                ORDER BY u.createdAt DESC
            """)
    List<UserStatDaily> findByUserIdAndPeriod(
            @Param("userId") UUID userId,
            @Param("start") Instant start,
            @Param("end") Instant end);

}
