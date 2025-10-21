package com.bunary.vocab.dto.request;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSetStudyReqDTO {
    private Long id;

    private Instant createdAt;

    private Instant updatedAt;

    private long WordSetId;

}
