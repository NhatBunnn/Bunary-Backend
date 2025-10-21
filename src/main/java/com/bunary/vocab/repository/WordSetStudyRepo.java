package com.bunary.vocab.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.WordSetStudy;

public interface WordSetStudyRepo extends JpaRepository<WordSetStudy, Long> {
    WordSetStudy findByWordSetIdAndUserId(Long wordSetId, UUID userId);

    boolean existsByWordSetIdAndUserId(Long wordSetId, UUID userId);
}
