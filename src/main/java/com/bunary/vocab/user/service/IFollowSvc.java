package com.bunary.vocab.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.user.dto.response.FollowResDTO;

public interface IFollowSvc {
    void toggleFollow(UUID followeeId);

    Page<FollowResDTO> findAllFollowerByCurrentUser(String keyword, Pageable pageable);
}
