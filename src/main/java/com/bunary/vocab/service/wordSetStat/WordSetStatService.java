package com.bunary.vocab.service.wordSetStat;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.WordSetStatResDTO;
import com.bunary.vocab.dto.request.WordSetStatReqDTO;
import com.bunary.vocab.mapper.WordSetStatMapper;
import com.bunary.vocab.repository.WordSetStatRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("wordSetStatService")
public class WordSetStatService implements IWordSetStatService {
    private final WordSetStatRepo wordSetStatRepo;
    private final WordSetStatMapper wordSetStatMapper;

    @Override
    public Page<WordSetStatResDTO> findAllWithUser(WordSetStatReqDTO wordSetStatReqDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllWithUser'");
    }

}
