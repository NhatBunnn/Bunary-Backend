package com.bunary.vocab.user.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.user.dto.internal.UserStatsParamDTO;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.mapper.UserStatsMapper;
import com.bunary.vocab.user.model.UserStats;
import com.bunary.vocab.user.repository.UserStatsRepo;
import com.bunary.vocab.user.service.IUserStatsSvc;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserStatsSvc implements IUserStatsSvc {
    // Repository
    private final UserStatsRepo userStatsRepo;
    private final WordSetRepository wordSetRepo;

    // Mapper
    private final UserStatsMapper userStatsMapper;

    // util
    private final SecurityUtil securityUtil;

    @Override
    public UserStatsResDTO adjust(UserStatsParamDTO param) {

        UUID userId = securityUtil.getCurrentUserId();

        UserStats userStats = userStatsRepo.findByUserId(userId)
                .orElse(null);

        if (userStats == null) {
            userStats = new UserStats();
            userStats.setUserId(userId);
        }

        // Learning stats
        userStats.setLearnedWordSetsCount(userStats.getLearnedWordSetsCount() + param.getLearnedWordSetsCount());

        // Points & rewards
        userStats.setPoint(userStats.getPoint() + param.getPoint());
        userStats.setSpark(userStats.getSpark() + param.getSpark());

        // Streak
        // Chưa làm

        userStatsRepo.save(userStats);

        UserStatsResDTO userStatsResDTO = userStatsMapper.toResponseDto(userStats);

        return userStatsResDTO;
    }

    @Override
    public UserStatsResDTO findByCurrentUser() {

        UUID userId = securityUtil.getCurrentUserId();

        UserStats userStats = userStatsRepo.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        UserStatsResDTO response = userStatsMapper.toResponseDto(userStats);

        Integer wordsetCreatedCount = this.wordSetRepo.findAllByCurrentUserId(userId);
        if (wordsetCreatedCount == null) {
            wordsetCreatedCount = 0;
        }

        response.setWordsetCreatedCount(wordsetCreatedCount);

        return response;
    }

}