package com.bunary.vocab.profile.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileReqDTO {
    private String banner;

    private String title;

    private String bio;
}
