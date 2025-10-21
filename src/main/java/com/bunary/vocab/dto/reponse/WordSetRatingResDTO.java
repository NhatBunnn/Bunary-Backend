package com.bunary.vocab.dto.reponse;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSetRatingResDTO {
    private Long id;

    private int value;

    private Instant createdAt;

    private Instant updatedAt;
}
