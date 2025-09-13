package com.bunary.vocab.dto.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatWindowStatusResponseDTO {
    private Long id;
    private String type;
    private String receiverId;
    private boolean isOpen;
}
