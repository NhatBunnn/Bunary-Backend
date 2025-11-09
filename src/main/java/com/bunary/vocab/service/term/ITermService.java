package com.bunary.vocab.service.term;

import java.util.List;

import com.bunary.vocab.dto.reponse.TermResDTO;

public interface ITermService {
    List<TermResDTO> search(String keyword);
}
