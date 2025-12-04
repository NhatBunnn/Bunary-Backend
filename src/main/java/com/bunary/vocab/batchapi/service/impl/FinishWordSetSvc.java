package com.bunary.vocab.batchapi.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.batchapi.dto.request.FinishWordSetReqDTO;
import com.bunary.vocab.batchapi.service.IFinishWordSetSvc;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.UserWordsetHistory.IUserWordsetHistoryService;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.service.IUserStatsSvc;
import com.bunary.vocab.user.service.IUserWsProgressSvc;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FinishWordSetSvc implements IFinishWordSetSvc {
    private final IUserStatsSvc userStatsSvc;
    private final IUserWsProgressSvc userWsProgressSvc;
    private final IUserWordsetHistoryService userWordsetHistoryService;

    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public void finish(Long wordSetId, FinishWordSetReqDTO request) {
        UUID userId = securityUtil.getCurrentUserId();

        this.userWordsetHistoryService.recordWordSetStudy(wordSetId);

        this.userWsProgressSvc.record(wordSetId, request.getProgress());

        /**
         * Update user stats
         * userStatsSvc.finishWordset(userId);
         * viết sau
         */

    }

}
