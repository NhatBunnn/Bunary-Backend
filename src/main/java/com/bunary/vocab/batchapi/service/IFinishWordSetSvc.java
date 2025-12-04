package com.bunary.vocab.batchapi.service;

import com.bunary.vocab.batchapi.dto.request.FinishWordSetReqDTO;

public interface IFinishWordSetSvc {
    void finish(Long wordSetId, FinishWordSetReqDTO request);
}
