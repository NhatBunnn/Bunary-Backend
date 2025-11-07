package com.bunary.vocab.dto.reponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageResDTO {
    private String messageContent;
    private String receiverId;
    private String senderId;
}
