package com.bunary.vocab.user.service;

import com.bunary.vocab.user.dto.request.UserWsProgressReqDTO;
import com.bunary.vocab.user.dto.response.UserWsProgressResDTO;

public interface IUserWsProgressSvc {
    UserWsProgressResDTO record(Long wordsetid, UserWsProgressReqDTO request);
}
