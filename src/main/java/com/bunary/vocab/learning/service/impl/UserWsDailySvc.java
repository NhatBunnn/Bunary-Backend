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
    public UserWsSummaryDTO findByPeriod() {
        // Get current user
        UUID currUserId = securityUtil.getCurrentUserId();

        // Get date
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate today = LocalDate.now(zone);

        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);

        Instant startDate = firstDayOfMonth.atStartOfDay(zone).toInstant();
        Instant endDate = today.plusDays(1).atStartOfDay(zone).toInstant();

        List<UserWordSetDaily> userWordSetDaily = userWsDailyRepo.findByUserAndPeriod(currUserId, startDate,
                endDate);

        // Convert to DTO
        List<UserWsDailyResDTO> userWsDailyResDTOs = userWordSetDaily.stream().map((uwsr) -> {
            UserWsDailyResDTO todayDto = this.userWsDailyMapper.toResponseDto(uwsr);

            LocalDate dto = uwsr
                    .getCreatedAt()
                    .atZone(zone)
                    .toLocalDate();

            todayDto.setCreatedAt(dto);

            return todayDto;
        }).toList();

        // Map
        Map<LocalDate, UserWsDailyResDTO> map = new LinkedHashMap<>();
        for (UserWsDailyResDTO dto : userWsDailyResDTOs) {
            map.put(dto.getCreatedAt(), dto);
        }

        // Get month
        List<UserWsDailyResDTO> monthDto = new ArrayList<>();
        for (LocalDate date = firstDayOfMonth; !date.isAfter(today); date = date.plusDays(1)) {
            UserWsDailyResDTO dto = map.getOrDefault(date, UserWsDailyResDTO.builder().createdAt(date).build());
            monthDto.add(dto);
        }

        // Get today
        UserWsDailyResDTO todayDto = monthDto.get(monthDto.size() - 1);

        // Get week
        List<UserWsDailyResDTO> weekDto = monthDto.stream()
                .filter((dto) -> dto.getCreatedAt().isAfter(weekStart.minusDays(1)))
                .toList();

        // convert to DTO
        UserWsSummaryDTO summaryDto = UserWsSummaryDTO.builder()
                .today(todayDto)
                .thisMonth(monthDto)
                .thisWeek(weekDto)
                .totals(
                        UserWsSummaryDTO.TotalsDTO.builder()
                                .thisWeek(calculateTotal(weekDto))
                                .thisMonth(calculateTotal(monthDto))
                                .build())
                .build();

        return summaryDto;
    }

    private UserWsDailyResDTO calculateTotal(List<UserWsDailyResDTO> list) {
        int totalPoint = 0;
        int totalSpark = 0;
        int totalLearned = 0;

        for (UserWsDailyResDTO dto : list) {
            totalPoint += dto.getPoint_earned();
            totalSpark += dto.getSpark_earned();
            totalLearned += dto.getLearned_count();
        }

        return UserWsDailyResDTO.builder()
                .point_earned(totalPoint)
                .spark_earned(totalSpark)
                .learned_count(totalLearned)
                .build();
    }

}
