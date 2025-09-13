package com.bunary.vocab.service.word;

import java.util.List;

import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.model.Word;
import com.bunary.vocab.model.WordSet;

public interface IWordService {
    List<Word> saveAll(List<Word> word);

    public List<WordReponseDTO> findByWordSetId(Long wordSet);

}
