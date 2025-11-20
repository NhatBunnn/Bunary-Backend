package com.bunary.vocab.dto.reponse;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ChatMessageResDTO {
    private String messageContent;
    private String receiverId;
    private String senderId;
    private Instant timestamp;
}
