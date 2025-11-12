package com.bunary.vocab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.WordResource;

public interface WordResourceRepo extends JpaRepository<WordResource, Long> {

    @EntityGraph(attributePaths = { "wordResourceImgs" })
    @Query("SELECT wr FROM WordResource wr WHERE LOWER(wr.word) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<WordResource> search(@Param("keyword") String keyword);
}
