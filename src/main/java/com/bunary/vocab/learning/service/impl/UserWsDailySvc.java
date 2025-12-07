package com.bunary.vocab.learning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.learning.dto.request.UserWsDailyReqDTO;
import com.bunary.vocab.learning.dto.response.UserWsDailyResDTO;
import com.bunary.vocab.learning.mapper.UserWsDailyMapper;
import com.bunary.vocab.learning.model.UserWordSetDaily;
import com.bunary.vocab.learning.repository.UserWsDailyRepo;
import com.bunary.vocab.learning.service.IUserWsDailySvc;
import com.bunary.vocab.model.User;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserWsDailySvc implements IUserWsDailySvc {
    // Repository
    private final UserWsDailyRepo userWsDailyRepo;

    // Mapper
    private final UserWsDailyMapper userWsDailyMapper;

    // Security
    private final SecurityUtil securityUtil;

    @Override
    public UserWsDailyResDTO record(UserWsDailyReqDTO request) {
        // Get current user
        UUID currUserId = this.securityUtil.getCurrentUserId();
        User user = User.builder().id(currUserId).build();

        // Get latest user word set daily
        UserWordSetDaily userWordSetDaily = this.userWsDailyRepo.findLatest(currUserId).orElse(null);

        // Main
        if (userWordSetDaily == null) {
            userWordSetDaily = new UserWordSetDaily();
            userWordSetDaily.setLearned_count(1);
            userWordSetDaily.setUser(user);
        } else {
            LocalDate createAt = LocalDate.ofInstant(userWordSetDaily.getCreatedAt(),
                    ZoneId.of("Asia/Ho_Chi_Minh"));
            LocalDate today = LocalDate.now();

            if (!createAt.isEqual(today)) {
                userWordSetDaily = new UserWordSetDaily();
                userWordSetDaily.setUser(user);
                userWordSetDaily.setLearned_count(1);
            } else {
                userWordSetDaily.setLearned_count(userWordSetDaily.getLearned_count() + 1);
            }

        }

        // Save
        userWordSetDaily = this.userWsDailyRepo.save(userWordSetDaily);

        // Convert to DTO
        UserWsDailyResDTO dto = this.userWsDailyMapper.toResponseDto(userWordSetDaily);

        return dto;
    }

    public UserWsDailyResDTO findByPeriod() {
        UUID currUserId = securityUtil.getCurrentUserId();

        return null;
    }
}
