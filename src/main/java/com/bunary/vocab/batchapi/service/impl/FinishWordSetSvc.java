package com.bunary.vocab.batchapi.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.batchapi.dto.request.FinishWordSetReqDTO;
import com.bunary.vocab.batchapi.dto.response.FinishWordSetResDTO;
import com.bunary.vocab.batchapi.service.IFinishWordSetSvc;
import com.bunary.vocab.learning.service.IUserWsRecentSvc;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.wordSetStat.IWordSetStatService;
import com.bunary.vocab.user.dto.internal.UserStatsParamDTO;
import com.bunary.vocab.user.service.IUserStatDailySvc;
import com.bunary.vocab.user.service.IUserStatsSvc;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FinishWordSetSvc implements IFinishWordSetSvc {
    private final IWordSetStatService wordSetStatService;
    private final IUserWsRecentSvc userWsRecentSvc;
    private final IUserStatDailySvc userWsDailySvc;
    private final IUserStatsSvc userStatsSvc;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public FinishWordSetResDTO finish(Long wordSetId) {

        // Update recent
        this.userWsRecentSvc.record(wordSetId);

        // Update wordset stat
        this.wordSetStatService.increaseStudy(wordSetId);

        // Adjust user stats
        UserStatsParamDTO userStatsParamDTO = new UserStatsParamDTO();
        userStatsParamDTO.setLearnedWordSetsCount(1);
        userStatsParamDTO.setPoint(10);
        userStatsParamDTO.setSpark(20);

        this.userStatsSvc.adjust(userStatsParamDTO);

        // Update daily
        this.userWsDailySvc.adjust(userStatsParamDTO);

        return FinishWordSetResDTO.builder()
                .spark(userStatsParamDTO.getSpark())
                .point(userStatsParamDTO.getPoint())
                .build();
    }

}
