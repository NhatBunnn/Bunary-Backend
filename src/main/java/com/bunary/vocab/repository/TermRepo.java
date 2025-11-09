package com.bunary.vocab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.Term;

public interface TermRepo extends JpaRepository<Term, Long> {

    @EntityGraph(attributePaths = { "medias" })
    @Query("SELECT t FROM Term t WHERE LOWER(t.word) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Term> search(@Param("keyword") String keyword);
}
