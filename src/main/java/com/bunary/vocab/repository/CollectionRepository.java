package com.bunary.vocab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Collection save(Collection collection);

    Page<Collection> findAll(Pageable pageable);

}