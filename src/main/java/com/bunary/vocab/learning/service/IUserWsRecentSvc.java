package com.bunary.vocab.learning.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.learning.dto.response.UserWsRecentResDTO;

public interface IUserWsRecentSvc {
    void record(Long wordsetId);

    Page<UserWsRecentResDTO> findAllByCurrentUser(Map<String, String> params, Pageable pageable);
}
