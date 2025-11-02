package com.bunary.vocab.service.UserWordsetHistory;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.UserWordsetHistoryResDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserWordsetHistoryMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.UserWordsetHistory;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.repository.UserWordsetHistoryRepo;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.security.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserWordsetHistoryService implements IUserWordsetHistoryService {
    private final UserWordsetHistoryRepo uWordSetHistoryRepo;
    private final WordSetRepository wordSetRepository;
    private final SecurityUtil securityUtil;
    private final UserWordsetHistoryMapper userWordsetHistoryMapper;

    @Override
    @Transactional
    public UserWordsetHistoryResDTO recordWordSetStudy(Long wordSetId) {

        if (!this.wordSetRepository.existsById(wordSetId)) {
            throw new ApiException(ErrorCode.ID_NOT_FOUND);
        }

        UUID currentUserId = UUID.fromString(securityUtil.getCurrentUser().get());

        UserWordsetHistory userWordsetHistory = this.uWordSetHistoryRepo.findByWordSetIdAndUserId(wordSetId,
                currentUserId);

        if (userWordsetHistory == null) {
            userWordsetHistory = new UserWordsetHistory();
            userWordsetHistory.setLastLearnedAt(Instant.now());
            userWordsetHistory.setUser(User.builder().id(currentUserId).build());
            userWordsetHistory.setWordSet(WordSet.builder().id(wordSetId).build());
        } else {
            userWordsetHistory.setLastLearnedAt(Instant.now());
        }

        return this.userWordsetHistoryMapper.convertToResDTO(this.uWordSetHistoryRepo.save(userWordsetHistory));
    }

}
