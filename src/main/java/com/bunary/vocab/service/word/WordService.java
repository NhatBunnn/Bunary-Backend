package com.bunary.vocab.service.word;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.mapper.WordMapper;
import com.bunary.vocab.model.Word;
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
    public Page<WordReponseDTO> findByWordSetId(Long wordSet, Pageable pageable) {
        Page<Word> page = this.wordRepository.findByWordSetId(wordSet, pageable);

        return this.wordMapper.convertToWordReponseDTO(page);
    }

}