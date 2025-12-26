package com.bunary.vocab.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class UserStatsResDTO {

    private int xp = 0;

    // Number of word sets created by the user
    private int learnedWordSetsCount = 0;
    private int learnedWordsCount = 0;
}
