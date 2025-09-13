package com.bunary.vocab.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.dto.request.WordRequestDTO;
import com.bunary.vocab.model.Word;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordMapper {
    public Word convertToWord(WordRequestDTO wordDTO) {
        Word word = new Word();
        word.setIpa(wordDTO.getIpa());
        word.setMeaning(wordDTO.getMeaning());
        word.setPartOfSpeech(wordDTO.getPartOfSpeech());
        word.setTerm(wordDTO.getTerm());
        word.setThumbnail(wordDTO.getThumbnail());

        return word;
    }

    public WordReponseDTO convertToWordReponseDTO(Word word) {
        WordReponseDTO wordDTO = new WordReponseDTO();
        wordDTO.setId(word.getId());
        wordDTO.setIpa(word.getIpa());
        wordDTO.setMeaning(word.getMeaning());
        wordDTO.setPartOfSpeech(word.getPartOfSpeech());
        wordDTO.setTerm(word.getTerm());
        wordDTO.setThumbnail(word.getThumbnail());

        return wordDTO;
    }

    public List<WordReponseDTO> convertToWordReponseDTO(List<Word> word) {
        List<WordReponseDTO> wordDTO = new ArrayList<>();

        for (Word w : word) {
            wordDTO.add(this.convertToWordReponseDTO(w));
        }

        return wordDTO;
    }
}
