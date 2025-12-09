package com.bunary.vocab.learning.service;

import com.bunary.vocab.learning.dto.response.UserWsDailyResDTO;
import com.bunary.vocab.learning.dto.response.UserWsSummaryDTO;

public interface IUserWsDailySvc {
    UserWsDailyResDTO record();

    public UserWsSummaryDTO findByPeriod();
}
