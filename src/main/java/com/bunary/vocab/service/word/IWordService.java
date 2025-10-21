package com.bunary.vocab.service.word;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.model.Word;

public interface IWordService {
    List<Word> saveAll(List<Word> word);

    public Page<WordReponseDTO> findByWordSetId(Long wordSet, Pageable pageable);

}
