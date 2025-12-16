package com.bunary.vocab.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.user.dto.response.FollowResDTO;
import com.bunary.vocab.user.mapper.FollowMapper;
import com.bunary.vocab.user.model.Follow;
import com.bunary.vocab.user.repository.FollowRepo;
import com.bunary.vocab.user.service.IFollowSvc;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowSvc implements IFollowSvc {
    // Repository
    private final FollowRepo followRepo;
    private final UserRepository userRepo;

    // Mapper
    private final FollowMapper followMapper;
    private final UserMapper userMapper;

    // Security
    private final SecurityUtil securityUtil;

    @Override
    public void toggleFollow(UUID followeeId) {
        // Get user
        UUID currUserId = this.securityUtil.getCurrentUserId();
        User currUser = this.userRepo.findById(currUserId).orElse(null);

        // Check user exist
        User followee = this.userRepo.findById(followeeId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        // Get follow
        Follow follow = this.followRepo.findByFollowerIdAndFolloweeId(currUserId, followeeId).orElse(null);

        if (follow == null) {
            follow = new Follow();
            follow.setFollower(currUser);
            follow.setFollowee(followee);

            this.followRepo.save(follow);
        } else {
            this.followRepo.delete(follow);
        }
    }

    @Override
    public Page<FollowResDTO> findAllFollowerByCurrentUser(String keyword, Pageable pageable) {

        UUID currUserId = this.securityUtil.getCurrentUserId();

        Page<Follow> followsPage = this.followRepo.findAllFollowerByKeywordAndFolloweeId(currUserId, keyword, pageable);

        List<FollowResDTO> followList = followsPage.stream().map((follow) -> {
            FollowResDTO dto = this.followMapper.toResponseDto(follow);

            UserResponseDTO followerDto = this.userMapper.convertToUserResponseDTO(follow.getFollower());

            dto.setFollower(followerDto);

            return dto;
        }).toList();

        return new PageImpl<>(followList, pageable, followsPage.getTotalElements());
    }

}
