package com.bunary.vocab.service.wordSetStat;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;

public interface IWordSetStatService {

    Page<WordSetReponseDTO> findAll(Map<String, String> params, Pageable pageable);
}
