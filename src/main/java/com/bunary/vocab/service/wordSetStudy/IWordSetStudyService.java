package com.bunary.vocab.service.wordSetStudy;

import com.bunary.vocab.dto.reponse.WordSetStudyResDTO;

public interface IWordSetStudyService {
    WordSetStudyResDTO createOrUpdate(Long wordSetId);
}
