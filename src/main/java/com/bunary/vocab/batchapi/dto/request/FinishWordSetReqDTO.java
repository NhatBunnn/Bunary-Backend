package com.bunary.vocab.batchapi.dto.request;

import com.bunary.vocab.learning.dto.request.UserWsRecentReqDTO;
import com.bunary.vocab.user.dto.request.UserWsProgressReqDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FinishWordSetReqDTO {
    private final UserWsProgressReqDTO progress;

    private final UserWsRecentReqDTO recent;
}
