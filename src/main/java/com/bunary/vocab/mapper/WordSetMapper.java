package com.bunary.vocab.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.user.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetMapper {
    private final SecurityUtil securityUtil;
    private final IUserService userService;

    public WordSetReponseDTO convertToWordSetReponseDTO(WordSet wordSet) {
        WordSetReponseDTO wordSetRep = new WordSetReponseDTO();
        wordSetRep.setId(wordSet.getId());
        wordSetRep.setTitle(wordSet.getTitle());
        wordSetRep.setDescription(wordSet.getDescription());
        wordSetRep.setThumbnail(wordSet.getThumbnail());

        // if (Hibernate.isInitialized(wordSet.getUser()) && wordSet.getUser() != null)
        // {
        // wordSetRep.setAuthor(this.userMapper.convertToUserResponseDTO(wordSet.getUser()));
        // }

        // if (Hibernate.isInitialized(wordSet.getCollections()) &&
        // wordSet.getCollections() != null) {
        // wordSetRep.getCollections()
        // .addAll(this.collectionMapper.convertToCollectionResDTO(wordSet.getCollections()));
        // }

        return wordSetRep;
    }

    public Page<WordSetReponseDTO> convertToWordSetReponseDTO(Page<WordSet> wordSet) {

        List<WordSetReponseDTO> wordSetDTO = new ArrayList<>();

        for (WordSet ws : wordSet) {
            wordSetDTO.add(convertToWordSetReponseDTO(ws));
        }

        return new PageImpl<>(wordSetDTO, wordSet.getPageable(), wordSet.getTotalElements());
    }

    public List<WordSetReponseDTO> convertToWordSetReponseDTO(List<WordSet> wordSet) {

        List<WordSetReponseDTO> wordSetDTO = new ArrayList<>();

        for (WordSet ws : wordSet) {
            wordSetDTO.add(convertToWordSetReponseDTO(ws));
        }

        return wordSetDTO;
    }

    public WordSet convertToWordSet(WordSetRequestDTO wordSetReq) {

        WordSet wordSet = new WordSet();

        if (wordSetReq.getTitle() != null)
            wordSet.setTitle(wordSetReq.getTitle());

        if (wordSetReq.getDescription() != null)
            wordSet.setDescription(wordSetReq.getDescription());

        if (wordSetReq.getThumbnail() != null)
            wordSet.setThumbnail(wordSetReq.getThumbnail());

        return wordSet;
    }

}
