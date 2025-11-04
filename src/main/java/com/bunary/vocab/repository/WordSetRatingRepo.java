package com.bunary.vocab.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.WordSetRating;

public interface WordSetRatingRepo extends JpaRepository<WordSetRating, Long> {
    WordSetRating findByWordSetIdAndUserId(Long wordSetId, UUID userId);

    boolean existsByWordSetIdAndUserId(Long wordSetId, UUID userId);

    @Query("SELECT AVG(r.value) FROM WordSetRating r WHERE r.wordSet.id = :wordSetId")
    Double findAvgRatingByWordSet(@Param("wordSetId") Long wordSetId);

    @EntityGraph(attributePaths = { "user" })
    Page<WordSetRating> findAllByWordSet_Id(Long wordSetId, Pageable pageable);
}
