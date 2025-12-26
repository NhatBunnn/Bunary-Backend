package com.bunary.vocab.learning.service.impl;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bunary.vocab.learning.dto.response.UserWsDailyResDTO;
import com.bunary.vocab.learning.dto.response.UserWsSummaryDTO;
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
    public UserWsDailyResDTO record() {
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");

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
            LocalDate createAt = LocalDate.ofInstant(userWordSetDaily.getCreatedAt(), zone);
            LocalDate today = LocalDate.now(zone);

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

    @Override
    public List<UserWsDailyResDTO> findLast30Days() {
        // Get current user
        UUID currUserId = securityUtil.getCurrentUserId();

        // Zone & dates
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate today = LocalDate.now(zone);
        LocalDate startDate30 = today.minusDays(29);
        Instant startInstant = startDate30.atStartOfDay(zone).toInstant();
        Instant endInstant = today.plusDays(1).atStartOfDay(zone).toInstant();

        List<UserWordSetDaily> userWordSetDaily = userWsDailyRepo.findByUserAndPeriod(currUserId, startInstant,
                endInstant);

        // Map ngày → DTO
        Map<LocalDate, UserWsDailyResDTO> map = userWordSetDaily.stream()
                .map(uwsr -> {
                    UserWsDailyResDTO dto = this.userWsDailyMapper.toResponseDto(uwsr);
                    dto.setCreatedAt(uwsr.getCreatedAt().atZone(zone).toLocalDate());
                    return dto;
                })
                .collect(Collectors.toMap(UserWsDailyResDTO::getCreatedAt, dto -> dto));

        // Tạo list 30 ngày, fill default nếu không có dữ liệu
        List<UserWsDailyResDTO> last30DaysDto = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            LocalDate date = today.minusDays(i); // hôm nay là ngày đầu tiên
            UserWsDailyResDTO dto = map.getOrDefault(date, UserWsDailyResDTO.builder().createdAt(date).build());
            last30DaysDto.add(dto);
        }

        return last30DaysDto;
    }

}
