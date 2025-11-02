package com.bunary.vocab.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        if (wordDTO.getId() != null)
            word.setId(wordDTO.getId());
        if (wordDTO.getIpa() != null)
            word.setIpa(wordDTO.getIpa());
        if (wordDTO.getMeaning() != null)
            word.setMeaning(wordDTO.getMeaning());
        if (wordDTO.getPartOfSpeech() != null)
            word.setPartOfSpeech(wordDTO.getPartOfSpeech());
        if (wordDTO.getTerm() != null)
            word.setTerm(wordDTO.getTerm());
        if (wordDTO.getThumbnail() != null)
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

    public List<Word> convertToWords(List<WordRequestDTO> wordDTO) {
        List<Word> words = new ArrayList<>();

        for (WordRequestDTO w : wordDTO) {
            words.add(this.convertToWord(w));
        }

        return words;
    }

    public Page<WordReponseDTO> convertToWordReponseDTO(Page<Word> word) {
        List<WordReponseDTO> wList = word.stream().map((w) -> {
            WordReponseDTO wordReponseDTO = new WordReponseDTO();
            wordReponseDTO.setId(w.getId());
            wordReponseDTO.setIpa(w.getIpa());
            wordReponseDTO.setTerm(w.getTerm());
            wordReponseDTO.setMeaning(w.getMeaning());
            wordReponseDTO.setPartOfSpeech(w.getPartOfSpeech());
            wordReponseDTO.setThumbnail(w.getThumbnail());

            return wordReponseDTO;
        }).toList();

        return new PageImpl<>(wList, word.getPageable(), word.getTotalElements());
    }
}
