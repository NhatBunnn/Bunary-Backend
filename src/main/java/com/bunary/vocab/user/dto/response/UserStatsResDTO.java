package com.bunary.vocab.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class UserStatsResDTO {

    private int xp = 0;

    // Number of word sets created by the user
    private int learnedWordSet = 0;
    private int learnedWord = 0;
}
