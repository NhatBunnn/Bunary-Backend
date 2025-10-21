package com.bunary.vocab.service.wordSetStudy;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.WordSetStudyResDTO;
import com.bunary.vocab.dto.request.WordSetStudyReqDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.WordSetStudyMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.WordSet;
import com.bunary.vocab.model.WordSetStudy;
import com.bunary.vocab.repository.WordSetRepository;
import com.bunary.vocab.repository.WordSetStudyRepo;
import com.bunary.vocab.security.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("wordSetStudyService")
public class WordSetStudyService implements IWordSetStudyService {
    private final WordSetStudyRepo wordSetStudyRepo;
    private final WordSetStudyMapper wordSetStudyMapper;
    private final SecurityUtil securityUtil;
    private final WordSetRepository wordSetRepository;

    @Transactional
    @Override
    public WordSetStudyResDTO recordStudy(Long wordSetId) {
        UUID currentUserId = UUID.fromString(securityUtil.getCurrentUser().get());

        if (!this.wordSetRepository.existsByIdAndUserId(wordSetId, currentUserId)) {
            throw new ApiException(ErrorCode.ID_NOT_FOUND);
        }

        WordSetStudy wordSetStudy = this.wordSetStudyRepo.findByWordSetIdAndUserId(wordSetId,
                currentUserId);

        if (wordSetStudy == null) {
            wordSetStudy = new WordSetStudy();
            wordSetStudy.setStudy_count(1);
            wordSetStudy.setUser(User.builder().id(currentUserId).build());
            wordSetStudy.setWordSet(WordSet.builder().id(wordSetId).build());
        } else {
            wordSetStudy.setStudy_count(wordSetStudy.getStudy_count() + 1);
        }

        return this.wordSetStudyMapper.convertToResDTO(
                this.wordSetStudyRepo.save(wordSetStudy));
    }
}
