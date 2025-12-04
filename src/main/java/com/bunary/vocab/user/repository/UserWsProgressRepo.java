package com.bunary.vocab.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.user.model.UserWordSetProgress;
import com.bunary.vocab.user.model.enums.StudyModeEnum;

public interface UserWsProgressRepo extends JpaRepository<UserWordSetProgress, Long> {
    Optional<UserWordSetProgress> findByUserIdAndWordSetIdAndStudyMode(UUID userId, Long wordSetId,
            StudyModeEnum studyMode);
}
