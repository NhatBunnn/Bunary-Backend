package com.bunary.vocab.batchapi.service;

import com.bunary.vocab.batchapi.dto.response.FinishWordSetResDTO;

public interface IFinishWordSetSvc {
    FinishWordSetResDTO finish(Long wordSetId);
}
