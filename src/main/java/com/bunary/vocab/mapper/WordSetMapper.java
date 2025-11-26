package com.bunary.vocab.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.model.WordSet;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordSetMapper {

    public WordSetReponseDTO convertToWordSetReponseDTO(WordSet wordSet) {
        WordSetReponseDTO wordSetRep = new WordSetReponseDTO();
        wordSetRep.setId(wordSet.getId());
        wordSetRep.setTitle(wordSet.getTitle());
        wordSetRep.setDescription(wordSet.getDescription());
        wordSetRep.setThumbnail(wordSet.getThumbnail());
        wordSetRep.setVisibility(wordSet.getVisibility());
        wordSetRep.setLevel(wordSet.getLevel());

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

        wordSet.setTitle(wordSetReq.getTitle());
        wordSet.setDescription(wordSetReq.getDescription());
        wordSet.setThumbnail(wordSetReq.getThumbnail());
        wordSet.setVisibility(wordSetReq.getVisibility());
        wordSet.setLevel(wordSetReq.getLevel());

        return wordSet;
    }

}
