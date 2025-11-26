package com.bunary.vocab.repository;

import com.bunary.vocab.model.relation.WordSetTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordSetTagRepository extends JpaRepository<WordSetTag, Long> {
}
