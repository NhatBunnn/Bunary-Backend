package com.bunary.vocab.profile.service;

import com.bunary.vocab.profile.dto.request.ProfileReqDTO;
import com.bunary.vocab.profile.dto.response.ProfileResDTO;

public interface IProfileSvc {
    ProfileResDTO update(ProfileReqDTO profileReqDTO);
}
