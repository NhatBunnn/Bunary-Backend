package com.bunary.vocab.user.dto.response;

import java.time.Instant;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FollowResDTO {
    private UserResponseDTO followee;

    private UserResponseDTO follower;

    private Instant createdAt;

}
