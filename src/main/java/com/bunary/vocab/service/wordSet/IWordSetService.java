package com.bunary.vocab.service.wordSet;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.model.WordSet;

public interface IWordSetService {
    WordSet save(WordSet wordSet);

    public Page<WordSet> findAllEntities(Pageable pageable);

    WordSetReponseDTO createWordSet(WordSetRequestDTO wordSet, MultipartFile file) throws Exception;

    Page<WordSetReponseDTO> findAll(Pageable pageable);

    public Page<WordSetReponseDTO> findAllWithAuthor(Pageable pageable);

    WordSetReponseDTO findById(Long id);

    Page<WordSetReponseDTO> findAllByCollectionId(Long collectionId, Pageable pageable);

}
