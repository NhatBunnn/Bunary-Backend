package com.bunary.vocab.user.service;

import com.bunary.vocab.user.dto.internal.UserStatsParamDTO;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;

public interface IUserStatsSvc {

    UserStatsResDTO findByCurrentUser();

    UserStatsResDTO adjust(UserStatsParamDTO param);
}
