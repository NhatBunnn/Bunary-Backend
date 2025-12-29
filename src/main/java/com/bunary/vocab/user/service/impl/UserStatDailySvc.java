package com.bunary.vocab.user.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bunary.vocab.learning.repository.UserStatDailyRepo;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.user.dto.internal.UserStatsParamDTO;
import com.bunary.vocab.user.dto.response.UserStatDailyResDTO;
import com.bunary.vocab.user.dto.response.UserStatsChartResDTO;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.dto.response.UserStatsTotalResDTO;
import com.bunary.vocab.user.dto.response.enums.StatsPeriodEnum;
import com.bunary.vocab.user.model.UserStatDaily;
import com.bunary.vocab.user.service.IUserStatDailySvc;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserStatDailySvc implements IUserStatDailySvc {
    // Repository
    private final UserStatDailyRepo userStatDailyRepo;
    private final WordSetRepository wordSetRepo;

    // Mapper

    // Security
    private final SecurityUtil securityUtil;

    private static final ZoneId currentZone = ZoneId.of("Asia/Ho_Chi_Minh");

    @Override
    public void adjust(UserStatsParamDTO param) {
        ZoneId zone = currentZone;

        // Get current user
        UUID currUserId = this.securityUtil.getCurrentUserId();
        User user = User.builder().id(currUserId).build();

        // Get latest user word set daily
        UserStatDaily userStatDaily = this.userStatDailyRepo.findLatest(currUserId).orElse(null);

        // Update or create
        if (userStatDaily == null) {
            userStatDaily = new UserStatDaily();
            userStatDaily.setUser(user);
        } else {
            LocalDate createAt = LocalDate.ofInstant(userStatDaily.getCreatedAt(), zone);
            LocalDate today = LocalDate.now(zone);

            if (!createAt.isEqual(today)) {
                userStatDaily = new UserStatDaily();
                userStatDaily.setUser(user);
            }

        }

        userStatDaily.setLearnedWordSetsCount(
                userStatDaily.getLearnedWordSetsCount() + param.getLearnedWordSetsCount());
        userStatDaily.setPoint(userStatDaily.getPoint() + param.getPoint());
        userStatDaily.setSpark(userStatDaily.getSpark() + param.getSpark());

        // Save
        userStatDaily = this.userStatDailyRepo.save(userStatDaily);

    }

    @Transactional
    @Override
    public UserStatDailyResDTO findByPeriod(StatsPeriodEnum period) {

        switch (period) {
            case TODAY:
                return this.findToday();
            case LAST_7_DAYS:
                return this.findLastDays(7, period);
            case LAST_28_DAYS:
                return this.findLastDays(28, period);
            default:
                return null;
        }

    }

    private UserStatDailyResDTO findToday() {

        // Get current user
        UUID currUserId = this.securityUtil.getCurrentUserId();
        User user = User.builder().id(currUserId).build();

        // Get today
        LocalDate today = LocalDate.now(currentZone);
        LocalDate start = today.minusDays(1);

        Instant startInstant = start
                .atStartOfDay(currentZone)
                .toInstant();

        Instant endInstant = today.plusDays(1)
                .atStartOfDay(currentZone)
                .toInstant();

        // Get latest user word set daily
        UserStatDaily userStatDaily = this.userStatDailyRepo.findLatest(currUserId).orElse(null);

        boolean createNew = false;

        if (userStatDaily == null) {
            createNew = true;
        } else {
            LocalDate createAt = LocalDate.ofInstant(userStatDaily.getCreatedAt(), currentZone);

            if (!createAt.isEqual(today)) {
                createNew = true;
            }
        }

        if (createNew) {
            userStatDaily = new UserStatDaily();
            userStatDaily.setUser(user);
            userStatDaily.setPoint(0);
            userStatDaily.setSpark(0);
        }

        // Count word set
        Integer learnedWordSetsCount = this.wordSetRepo.countByUserIdAndPeriod(currUserId, startInstant, endInstant);

        if (learnedWordSetsCount == null) {
            userStatDaily.setLearnedWordSetsCount(0);
        }

        // Return
        return UserStatDailyResDTO.builder()
                .period(StatsPeriodEnum.TODAY)
                .total(UserStatsTotalResDTO.builder()
                        .learnedWordSetsCount(userStatDaily.getLearnedWordSetsCount())
                        .wordsetCreatedCount(learnedWordSetsCount)
                        .point(userStatDaily.getPoint())
                        .spark(userStatDaily.getSpark())
                        .build())
                .chart(
                        List.of(
                                UserStatsChartResDTO.builder()
                                        .date(today)
                                        .learnedWordSetsCount(userStatDaily.getLearnedWordSetsCount())
                                        .wordsetCreatedCount(learnedWordSetsCount.intValue())
                                        .point(userStatDaily.getPoint())
                                        .spark(userStatDaily.getSpark())
                                        .build()))
                .build();
    }

    private UserStatDailyResDTO findLastDays(int days, StatsPeriodEnum period) {

        // Get current user
        UUID currUserId = this.securityUtil.getCurrentUserId();
        User user = User.builder().id(currUserId).build();

        // Get today
        LocalDate today = LocalDate.now(currentZone);
        LocalDate start = today.minusDays(days - 1);

        Instant startInstant = start
                .atStartOfDay(currentZone)
                .toInstant();

        Instant endInstant = today.plusDays(1)
                .atStartOfDay(currentZone)
                .toInstant();

        List<UserStatDaily> userStatDailyList = this.userStatDailyRepo
                .findByUserIdAndPeriod(currUserId, startInstant, endInstant);

        List<WordSet> wordSetList = this.wordSetRepo.findByUserIdAndPeriod(currUserId, startInstant, endInstant);

        // Chart
        Map<LocalDate, UserStatDaily> userStatDailyMap = new HashMap<>();
        Map<LocalDate, Integer> wordSetMap = new HashMap<>();

        for (UserStatDaily userStatDaily : userStatDailyList) {
            userStatDailyMap.put(
                    userStatDaily.getCreatedAt().atZone(currentZone).toLocalDate(),
                    userStatDaily);
        }

        for (WordSet wordSet : wordSetList) {
            LocalDate date = wordSet.getCreatedAt()
                    .atZone(currentZone)
                    .toLocalDate();

            wordSetMap.merge(date, 1, (old, val) -> old + val);
        }

        List<UserStatsChartResDTO> userStatsChartResDTOList = new ArrayList<>();

        for (int i = 0; i < days; i++) {
            LocalDate date = start.plusDays(i);

            UserStatDaily userStatDaily = userStatDailyMap.get(date);
            int wordSetCount = wordSetMap.getOrDefault(date, 0);

            if (userStatDaily == null) {
                userStatDaily = new UserStatDaily();
                userStatDaily.setUser(user);
                userStatDaily.setPoint(0);
                userStatDaily.setSpark(0);
            }

            userStatsChartResDTOList.add(UserStatsChartResDTO.builder()
                    .date(date)
                    .learnedWordSetsCount(userStatDaily.getLearnedWordSetsCount())
                    .wordsetCreatedCount(wordSetCount)
                    .point(userStatDaily.getPoint())
                    .spark(userStatDaily.getSpark())
                    .build());
        }

        // total
        int learnedWordSetsCount = 0;
        int wordsetCreatedCount = 0;
        int point = 0;
        int spark = 0;

        for (UserStatsChartResDTO userStatsChartResDTO : userStatsChartResDTOList) {

            learnedWordSetsCount += userStatsChartResDTO.getLearnedWordSetsCount();
            wordsetCreatedCount += userStatsChartResDTO.getWordsetCreatedCount();
            point += userStatsChartResDTO.getPoint();
            spark += userStatsChartResDTO.getSpark();

        }

        return UserStatDailyResDTO.builder()
                .period(period)
                .total(UserStatsTotalResDTO.builder()
                        .learnedWordSetsCount(learnedWordSetsCount)
                        .wordsetCreatedCount(wordsetCreatedCount)
                        .point(point)
                        .spark(spark)
                        .build())
                .chart(userStatsChartResDTOList)
                .build();
    }

}
