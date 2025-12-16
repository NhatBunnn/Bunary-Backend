package com.bunary.vocab.user.dto.request;

import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FollowReqDTO {
    private UserResponseDTO followee;

}
