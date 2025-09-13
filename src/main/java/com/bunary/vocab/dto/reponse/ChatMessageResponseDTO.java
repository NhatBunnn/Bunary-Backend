package com.bunary.vocab.dto.reponse;

import java.time.Instant;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageResponseDTO {
    private Long id;
    private String type;
    private String messageContent;
    private String status;
    private Instant timestamp;

    private UUID senderId;
    private UUID receiverId;

}
