package com.bunary.vocab.service.word;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.dto.request.WordRequestDTO;
import com.bunary.vocab.mapper.WordMapper;
import com.bunary.vocab.model.Word;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.WordRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WordService implements IWordService {
    private final WordRepository wordRepository;
    private final WordMapper wordMapper;

    public List<Word> saveAll(List<Word> word) {
        return this.wordRepository.saveAll(word);
    }

    @Override
    public List<WordReponseDTO> findByWordSetId(Long wordSet) {
        return this.wordMapper.convertToWordReponseDTO(this.wordRepository.findByWordSetId(wordSet));
    }

}