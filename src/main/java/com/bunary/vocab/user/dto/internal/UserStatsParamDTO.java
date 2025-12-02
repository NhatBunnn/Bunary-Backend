package com.bunary.vocab.user.dto.internal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatsParamDTO {

    // Exp
    private Integer xp;

    // Learned
    private Integer learnedWordSet;
    private Integer learnedWord;

    // Streak
    private Integer streak;
    private Integer maxStreak;
}
