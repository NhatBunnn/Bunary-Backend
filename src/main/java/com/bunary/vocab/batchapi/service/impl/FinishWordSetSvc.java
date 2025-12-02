package com.bunary.vocab.batchapi.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.batchapi.service.IFinishWordSetSvc;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.service.IUserStatsSvc;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FinishWordSetSvc implements IFinishWordSetSvc {
    private final IUserStatsSvc userStatsSvc;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public void finish(String wordSetId) {
        UUID userId = securityUtil.getCurrentUserId();

        // Update user stats
        userStatsSvc.finishWordset(userId);

        // viết sau

    }

}
