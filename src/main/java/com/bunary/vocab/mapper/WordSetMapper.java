package com.bunary.vocab.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        wordSetRep.setAuthorId(wordSet.getUser().getId());

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

        Optional<User> user = Optional.of(new User());
        user = this.userService.findById(UUID.fromString(this.securityUtil.getCurrentUser().get()));

        WordSet wordSet = new WordSet();
        wordSet.setTitle(wordSetReq.getTitle());
        wordSet.setDescription(wordSetReq.getDescription());
        wordSet.setThumbnail(wordSetReq.getThumbnail());
        wordSet.setCreatedAt(Instant.now());
        wordSet.setUser(user.get());

        return wordSet;
    }

}
