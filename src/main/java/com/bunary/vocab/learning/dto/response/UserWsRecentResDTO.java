package com.bunary.vocab.learning.dto.response;

import java.time.Instant;

import com.bunary.vocab.dto.reponse.WordSetReponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class UserWsRecentResDTO {
    private Instant lastLearnedAt;

    private WordSetReponseDTO wordSet;
}
