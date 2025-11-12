package com.bunary.vocab.service.term;

import java.util.List;

import com.bunary.vocab.dto.reponse.WordResourceResDTO;

public interface IWordResourceService {
    List<WordResourceResDTO> search(String keyword);
}
