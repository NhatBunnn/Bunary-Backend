package com.bunary.vocab.dto.reponse;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationResDTO {
    private Long id;
    private String type; // "rate", "like", "comment"...
    private String message;
    private String targetType; // "wordset", "post", "comment"
    private String targetId; // id của đối tượng bị tác động
    private Instant timestamp;
    private UserResponseDTO fromUser;
}
