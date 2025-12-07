package com.bunary.vocab.learning.service;

import com.bunary.vocab.learning.dto.request.UserWsDailyReqDTO;
import com.bunary.vocab.learning.dto.response.UserWsDailyResDTO;

public interface IUserWsDailySvc {
    UserWsDailyResDTO record(UserWsDailyReqDTO request);
}
