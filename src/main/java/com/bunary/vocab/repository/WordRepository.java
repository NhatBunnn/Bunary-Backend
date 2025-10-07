package com.bunary.vocab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByWordSetId(Long wordSetId);

}
