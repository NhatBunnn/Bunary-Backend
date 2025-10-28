package com.bunary.vocab.service.wordSetStat;

import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.model.WordSetStat;
import com.bunary.vocab.repository.WordSetStatRepo;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("wordSetStatService")
public class WordSetStatService implements IWordSetStatService {
    private final WordSetStatRepo wordSetStatRepo;

    @Transactional
    @Override
    public void increaseView(Long wordsetId) {
        WordSetStat stat = this.wordSetStatRepo.findByWordSetId(wordsetId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        stat.setViewCount(stat.getViewCount() + 1);

        wordSetStatRepo.save(stat);
    }

    @Transactional
    public void increaseStudy(Long wordsetId) {
        WordSetStat stat = this.wordSetStatRepo.findByWordSetId(wordsetId)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));
        stat.setStudyCount(stat.getStudyCount() + 1);
        wordSetStatRepo.save(stat);
    }

}
