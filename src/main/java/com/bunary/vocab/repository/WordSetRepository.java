package com.bunary.vocab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.model.WordSet;

public interface WordSetRepository extends JpaRepository<WordSet, Long> {
    WordSet save(WordSet wordSet);

    Page<WordSet> findAll(Pageable pageable);

    // Làm thử nào rảnh sửa -> construcotor đang bị set cứng ko linh hoạt
    // @Query("""
    // SELECT new com.bunary.vocab.dto.reponse.WordSetReponseDTO(
    // w.id, w.title, w.description, w.thumbnail, u.id,
    // new com.bunary.vocab.dto.reponse.UserResponseDTO(
    // u.fullName, u.avatar
    // )
    // )
    // FROM WordSet w
    // JOIN w.user u
    // """)
    // Page<WordSetReponseDTO> findAllWithAuthor(Pageable pageable);

    @EntityGraph(attributePaths = { "user" })
    @Query("SELECT w FROM WordSet w")
    Page<WordSet> findAllWithAuthor(Pageable pageable);

    Optional<WordSet> findById(Long id);

    @EntityGraph(attributePaths = { "user", "collections" })
    @Query("SELECT w FROM WordSet w WHERE w.id = :id")
    Optional<WordSet> findByIdWithUserAndCollection(Long id);

    @EntityGraph(attributePaths = { "Words" })
    @Query("SELECT w FROM WordSet w WHERE w.id = :id")
    Optional<WordSet> findByIdWithWords(Long id);

    Page<WordSet> findByCollections_Id(Long collectionId, Pageable pageable);

    @Query("""
            SELECT COUNT(w.id)
            FROM Word w
            WHERE w.id IN :wordIds AND w.wordSet.id = :wordSetId
            """)
    long countWordsInWordSet(@Param("wordSetId") Long wordSetId, @Param("wordIds") List<Long> wordIds);

}
