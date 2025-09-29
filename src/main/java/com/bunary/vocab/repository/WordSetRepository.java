package com.bunary.vocab.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.model.WordSet;

public interface WordSetRepository extends JpaRepository<WordSet, Long> {
    WordSet save(WordSet wordSet);

    Page<WordSet> findAll(Pageable pageable);

    @Query("""
                SELECT new com.bunary.vocab.dto.reponse.WordSetReponseDTO(
                    w.id, w.title, w.description, w.thumbnail, u.id,
                    new com.bunary.vocab.dto.reponse.UserResponseDTO(
                        u.fullName, u.avatar
                    )
                )
                FROM WordSet w
                JOIN w.user u
            """)
    Page<WordSetReponseDTO> findAllWithAuthor(Pageable pageable);

    Optional<WordSet> findById(Long id);

    Page<WordSet> findByCollections_Id(Long collectionId, Pageable pageable);

}
