package com.bunary.vocab.learning.service;

import java.util.List;

import com.bunary.vocab.learning.dto.response.UserWsDailyResDTO;

public interface IUserWsDailySvc {
    UserWsDailyResDTO record();

    List<UserWsDailyResDTO> findLast30Days();
}
