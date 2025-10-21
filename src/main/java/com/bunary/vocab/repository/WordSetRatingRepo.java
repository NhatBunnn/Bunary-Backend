package com.bunary.vocab.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.WordSetRating;

public interface WordSetRatingRepo extends JpaRepository<WordSetRating, Long> {
    WordSetRating findByWordSetIdAndUserId(Long wordSetId, UUID userId);

    boolean existsByWordSetIdAndUserId(Long wordSetId, UUID userId);
}
