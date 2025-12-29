package com.bunary.vocab.user.service;

import java.util.List;

import com.bunary.vocab.user.dto.internal.UserStatsParamDTO;
import com.bunary.vocab.user.dto.response.UserStatDailyResDTO;
import com.bunary.vocab.user.dto.response.enums.StatsPeriodEnum;

public interface IUserStatDailySvc {
    void adjust(UserStatsParamDTO param);

    UserStatDailyResDTO findByPeriod(StatsPeriodEnum period);
}
