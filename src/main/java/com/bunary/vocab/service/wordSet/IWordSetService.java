package com.bunary.vocab.service.wordSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.model.WordSet;

public interface IWordSetService {
    WordSet save(WordSet wordSet);

    public Page<WordSet> findAllEntities(Pageable pageable);

    WordSetReponseDTO update(WordSetRequestDTO wordSet, Long wordSetId, MultipartFile file) throws Exception;

    void removeWordSet(Long wordsetId);

    WordSetReponseDTO createWordSet(WordSetRequestDTO wordSet, MultipartFile file) throws Exception;

    Page<WordSetReponseDTO> findAll(Pageable pageable);

    Page<WordSetReponseDTO> findAllByCurrentUser(Pageable pageable);

    Page<WordSetReponseDTO> findAllWithAuthor(Pageable pageable);

    WordSetReponseDTO findById(Long id);

    Page<WordSetReponseDTO> findAllByCollectionId(Long collectionId, Pageable pageable);

    Page<WordSetReponseDTO> findAllByVisibilityWithUser(String visibility, Pageable pageable);

    WordSetReponseDTO findByIdWithUserAndCollection(Long id);

    WordSetReponseDTO findByIdWithWords(Long id);

    void recalculateAllPopularityScores();
}
