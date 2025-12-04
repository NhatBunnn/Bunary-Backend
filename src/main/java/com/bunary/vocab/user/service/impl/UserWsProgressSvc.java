package com.bunary.vocab.user.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.UserRepository;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.wordSetStat.IWordSetStatService;
import com.bunary.vocab.user.dto.request.UserWsProgressReqDTO;
import com.bunary.vocab.user.dto.response.UserWsProgressResDTO;
import com.bunary.vocab.user.mapper.UserWsProgressMapper;
import com.bunary.vocab.user.model.UserWordSetProgress;
import com.bunary.vocab.user.repository.UserWsProgressRepo;
import com.bunary.vocab.user.service.IUserWsProgressSvc;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserWsProgressSvc implements IUserWsProgressSvc {
    private final UserWsProgressRepo userWsProgressRepo;
    private final UserWsProgressMapper userWsProgressMapper;
    private final SecurityUtil securityUtil;
    private final WordSetRepository wordSetRepository;
    private final IWordSetStatService wordSetStatService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserWsProgressResDTO record(Long wordsetId, UserWsProgressReqDTO request) {

        WordSet wordSet = this.wordSetRepository.findById(wordsetId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        UUID userId = this.securityUtil.getCurrentUserId();
        User currentUser = User.builder().id(userId).build();

        UserWordSetProgress userWsProgress = this.userWsProgressRepo
                .findByUserIdAndWordSetIdAndStudyMode(userId, wordSet.getId(), request.getStudyMode())
                .orElse(null);

        if (userWsProgress == null) {
            userWsProgress = new UserWordSetProgress();
            userWsProgress.setUser(currentUser);
            userWsProgress.setWordSet(wordSet);
            userWsProgress.setStudyMode(request.getStudyMode());
            userWsProgress.setLearnCount(userWsProgress.getLearnCount() + 1);
            userWsProgress.setLastStudiedAt(Instant.now());
        } else {
            userWsProgress.setLearnCount(userWsProgress.getLearnCount() + 1);
            userWsProgress.setLastStudiedAt(Instant.now());
        }

        this.wordSetStatService.increaseStudy(wordSet.getId());

        userWsProgress = this.userWsProgressRepo.save(userWsProgress);

        return this.userWsProgressMapper.toResponseDto(userWsProgress);
    }
}
