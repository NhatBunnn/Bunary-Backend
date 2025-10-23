package com.bunary.vocab.scheduler.wordSet;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.service.wordSet.IWordSetService;

import lombok.AllArgsConstructor;

@Component
@RestController
@AllArgsConstructor
public class WordSetStatScheduler {
    private final IWordSetService wordSetService;

    // tự động cập nhật mỗi ngày lúc 3h sáng

    @PostMapping("/schedule/wordset")
    @Scheduled(cron = "0 0 3 * * ?")
    public void autoRecalculatePopularityScores() {
        wordSetService.recalculateAllPopularityScores();
    }
}
