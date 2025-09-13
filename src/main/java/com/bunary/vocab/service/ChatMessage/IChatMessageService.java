package com.bunary.vocab.service.ChatMessage;

import java.util.List;
import java.util.UUID;

import com.bunary.vocab.dto.reponse.ChatMessageResponseDTO;
import com.bunary.vocab.model.ChatMessage;

public interface IChatMessageService {
    public ChatMessage saveChatMessage(ChatMessage chatMessage);

    public void saveAllAsync(List<ChatMessage> chatMessage);

    public List<ChatMessageResponseDTO> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);
}
