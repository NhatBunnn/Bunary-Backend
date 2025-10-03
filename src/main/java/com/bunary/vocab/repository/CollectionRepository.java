package com.bunary.vocab.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
        Collection save(Collection collection);

        Page<Collection> findAll(Pageable pageable);

        // @EntityGraph(attributePaths = { "user" })
        Optional<Collection> findById(Long id);

        @EntityGraph(attributePaths = { "user" })
        @Query("SELECT c FROM Collection c")
        Page<Collection> findAllWithUser(Pageable pageable);

        @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
                        "FROM Collection col JOIN col.wordSets c " +
                        "WHERE col.id = :collectionId AND c.id = :wordSetId")
        boolean existsWordSetInCollection(@Param("collectionId") Long collectionId,
                        @Param("wordSetId") Long wordSetId);
}