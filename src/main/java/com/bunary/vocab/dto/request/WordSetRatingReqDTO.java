package com.bunary.vocab.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSetRatingReqDTO {
    private int value;

    private String comment;

    private long wordSetId;
}
