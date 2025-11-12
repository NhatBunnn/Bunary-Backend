package com.bunary.vocab.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.enums.VisibilityEnum;

public interface WordSetRepository extends JpaRepository<WordSet, Long>, JpaSpecificationExecutor<WordSet> {
        WordSet save(WordSet wordSet);

        boolean existsByIdAndUserId(Long id, UUID userId);

        Page<WordSet> findAll(Pageable pageable);

        @EntityGraph(attributePaths = { "user" })
        @Query("SELECT w FROM WordSet w")
        Page<WordSet> findAllWithAuthor(Pageable pageable);

        Page<WordSet> findAllByUserId(UUID userId, Pageable pageable);

        @EntityGraph(attributePaths = { "user", "collections", "wordSetStat" })
        Optional<WordSet> findById(Long id);

        @EntityGraph(attributePaths = { "user", "collections" })
        @Query("SELECT w FROM WordSet w WHERE w.id = :id")
        Optional<WordSet> findByIdWithUserAndCollection(@Param("id") Long id);

        @EntityGraph(attributePaths = { "Words" })
        @Query("SELECT w FROM WordSet w WHERE w.id = :id")
        Optional<WordSet> findByIdWithWords(Long id);

        Page<WordSet> findByCollections_Id(Long collectionId, Pageable pageable);

        @EntityGraph(attributePaths = { "user" })
        @Query("SELECT w FROM WordSet w WHERE w.visibility = :visibilityEnum")
        Page<WordSet> findAllByVisibilityWithUser(VisibilityEnum visibilityEnum, Pageable pageable);

        Page<WordSet> findAll(Specification<WordSet> specification, Pageable pageable);

        @Query("""
                        SELECT COUNT(w.id)
                        FROM Word w
                        WHERE w.id IN :wordIds AND w.wordSet.id = :wordSetId
                        """)
        Long countWordsInWordSet(@Param("wordSetId") Long wordSetId, @Param("wordIds") List<Long> wordIds);

        @Query("""
                        SELECT COUNT(w.id)
                        FROM Word w
                        WHERE w.wordSet.id = :wordSetId
                        """)
        Long countWords(@Param("wordSetId") Long wordSetId);

        @Query("SELECT w FROM WordSet w " +
                        "WHERE w.title LIKE CONCAT('%', :keyword, '%') " +
                        "AND w.visibility = :visibility")
        Page<WordSet> searchWithVisibility(
                        @Param("keyword") String keyword,
                        @Param("visibility") VisibilityEnum visibility,
                        Pageable pageable);

}
