package com.bunary.vocab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.WordSetStat;

public interface WordSetStatRepo extends JpaRepository<WordSetStat, Long> {
    Optional<WordSetStat> findByWordSetId(Long id);
}
