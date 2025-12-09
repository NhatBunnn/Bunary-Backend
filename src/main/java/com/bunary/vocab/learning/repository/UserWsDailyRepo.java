package com.bunary.vocab.learning.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

  @Query("""
          SELECT u
          FROM UserWordSetDaily u
          WHERE u.user.id = :userId
            AND u.createdAt >= :start
            AND u.createdAt < :end
          ORDER BY u.createdAt ASC
      """)
  List<UserWordSetDaily> findByUserAndPeriod(
      @Param("userId") UUID userId,
      @Param("start") Instant start,
      @Param("end") Instant end);

}
