package com.bunary.vocab.user.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserStatsParamDTO {

    // Learning stats
    private int learnedWordSetsCount = 0;

    // Points & rewards
    private int point;
    private int spark;

    // Streak
    private int streak;
    private int max_streak;
}
