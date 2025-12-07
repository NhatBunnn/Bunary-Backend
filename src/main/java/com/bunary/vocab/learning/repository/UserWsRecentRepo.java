package com.bunary.vocab.learning.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.learning.model.UserWordSetRecent;

public interface UserWsRecentRepo extends JpaRepository<UserWordSetRecent, Long> {
    Optional<UserWordSetRecent> findByWordSetIdAndUserId(Long wordSetId, UUID userId);
}
