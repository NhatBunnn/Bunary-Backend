package com.bunary.vocab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.WordSetComment;

public interface WordSetCommentRepo extends JpaRepository<WordSetComment, Long> {

    @EntityGraph(attributePaths = { "user" })
    Page<WordSetComment> findAllByWordSet_Id(Long wordSetId, Pageable pageable);
}
