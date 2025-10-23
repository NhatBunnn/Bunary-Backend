package com.bunary.vocab.service.wordSetStat;

import org.springframework.data.domain.Page;

import com.bunary.vocab.dto.reponse.WordSetStatResDTO;
import com.bunary.vocab.dto.request.WordSetStatReqDTO;

public interface IWordSetStatService {

    Page<WordSetStatResDTO> findAllWithUser(WordSetStatReqDTO wordSetStatReqDTO);
}
