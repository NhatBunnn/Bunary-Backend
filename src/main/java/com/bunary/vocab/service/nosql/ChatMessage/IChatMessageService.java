package com.bunary.vocab.service.nosql.ChatMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bunary.vocab.dto.reponse.ChatMessageResDTO;

public interface IChatMessageService {
    void sendMessage(ChatMessageResDTO chatMessageResDTO);

    Page<ChatMessageResDTO> findAllChatMessages(String receiverId, Pageable pageable);
}
