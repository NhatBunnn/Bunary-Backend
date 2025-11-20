package com.bunary.vocab.model.nosql;

import java.time.Instant;

import jakarta.persistence.Id;
import lombok.*;

import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_messages")
public class ChatMessage {

    @Id
    private String id;

    private String messageContent;

    private Instant timestamp;

    private String senderId;

    private String receiverId;
}
