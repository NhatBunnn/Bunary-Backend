package com.bunary.vocab.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.UserWordsetHistory;

public interface UserWordsetHistoryRepo extends JpaRepository<UserWordsetHistory, Long> {
    UserWordsetHistory findByWordSetIdAndUserId(Long wordSetId, UUID userId);
}
