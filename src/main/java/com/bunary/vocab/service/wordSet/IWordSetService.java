package com.bunary.vocab.service.wordSet;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.model.WordSet;

public interface IWordSetService {
    WordSet save(WordSet wordSet);

    Page<WordSet> findAllEntities(Pageable pageable);

    WordSetReponseDTO update(WordSetRequestDTO wordSet, Long wordSetId, MultipartFile file) throws Exception;

    void removeWordSet(Long wordsetId);

    WordSetReponseDTO createWordSet(WordSetRequestDTO wordSet, MultipartFile file) throws Exception;

    Page<WordSetReponseDTO> findAll(Map<String, String> params, Pageable pageable);

    Page<WordSetReponseDTO> findAllByCurrentUser(Pageable pageable);

    Page<WordSetReponseDTO> findAllWithAuthor(Pageable pageable);

    Page<WordSetReponseDTO> findAllByCollectionId(Long collectionId, Pageable pageable);

    Page<WordSetReponseDTO> findAllByVisibilityWithUser(String visibility, Pageable pageable);

    WordSetReponseDTO findById(Long id);

    WordSetReponseDTO findByIdWithWords(Long id);

    void recalculateAllPopularityScores();

    Page<WordSetReponseDTO> searchWordSets(String keyword, Pageable pageable);
}
