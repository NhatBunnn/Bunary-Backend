package com.bunary.vocab.user.service;

import java.util.UUID;

import com.bunary.vocab.user.dto.response.UserStatsResDTO;

public interface IUserStatsSvc {
    UserStatsResDTO finishWordset(UUID userId);

    UserStatsResDTO findByUserId(UUID userId);
}
