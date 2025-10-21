package com.bunary.vocab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
    Page<Word> findByWordSetId(Long wordSetId, Pageable pageable);

}
