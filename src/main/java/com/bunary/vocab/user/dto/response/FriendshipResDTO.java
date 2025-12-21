package com.bunary.vocab.user.dto.response;

import java.time.Instant;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.user.model.enums.FriendshipStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FriendshipResDTO {
    private Long id;
    private UserResponseDTO requester;
    private UserResponseDTO addressee;
    private Instant createdAt;

    private FriendshipStatusEnum friendshipStatus;

}
