package com.bunary.vocab.batchapi.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.batchapi.dto.request.FinishWordSetReqDTO;
import com.bunary.vocab.batchapi.service.IFinishWordSetSvc;
import com.bunary.vocab.learning.service.IUserWsRecentSvc;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.wordSetStat.IWordSetStatService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FinishWordSetSvc implements IFinishWordSetSvc {
    private final IWordSetStatService wordSetStatService;
    private final IUserWsRecentSvc userWsRecentSvc;

    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public void finish(Long wordSetId, FinishWordSetReqDTO request) {
        UUID userId = securityUtil.getCurrentUserId();

        this.userWsRecentSvc.record(wordSetId);

        this.wordSetStatService.increaseStudy(wordSetId);

        /**
         * Update user stats
         * userStatsSvc.finishWordset(userId);
         * viết sau
         */

    }

}
