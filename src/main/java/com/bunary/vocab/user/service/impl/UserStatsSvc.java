package com.bunary.vocab.user.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.user.dto.internal.UserStatsParamDTO;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.mapper.UserStatsMapper;
import com.bunary.vocab.user.model.UserStats;
import com.bunary.vocab.user.repository.UserStatsRepo;
import com.bunary.vocab.user.service.IUserStatsSvc;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserStatsSvc implements IUserStatsSvc {
    // Repository
    private final UserStatsRepo userStatsRepo;

    // Mapper
    private final UserStatsMapper userStatsMapper;

    @Override
    @Transactional
    public UserStatsResDTO finishWordset(UUID userId) {

        UserStats userStats = userStatsRepo.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        userStats.setPoint(userStats.getPoint() + 10);
        userStats.setLearnedWordSetsCount(userStats.getLearnedWordSetsCount() + 1);

        userStatsRepo.save(userStats);

        UserStatsResDTO userStatsResDTO = userStatsMapper.toResponseDto(userStats);

        return userStatsResDTO;
    }

    @Override
    public UserStatsResDTO findByUserId(UUID userId) {

        UserStats userStats = userStatsRepo.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        UserStatsResDTO response = userStatsMapper.toResponseDto(userStats);

        return response;
    }

}