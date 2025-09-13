package com.bunary.vocab.service.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.ChatMessageResponseDTO;
import com.bunary.vocab.mapper.ChatMessageMapper;
import com.bunary.vocab.model.ChatMessage;
import com.bunary.vocab.repository.ChatMessageRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChatMessageService implements IChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        return this.chatMessageRepository.save(chatMessage);
    }

    @Override
    @Async
    public void saveAllAsync(List<ChatMessage> chatMessage) {
        this.chatMessageRepository.saveAll(chatMessage);
    }

    @Override
    public List<ChatMessageResponseDTO> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId) {
        List<ChatMessage> chatMessages = this.chatMessageRepository.findBySenderIdAndReceiverId(senderId, receiverId);

        List<ChatMessageResponseDTO> chatMessageDTO = new ArrayList<>();

        for (ChatMessage c : chatMessages) {
            chatMessageDTO.add(this.chatMessageMapper.convertToChatMessageResponseDTO(c));
        }

        return chatMessageDTO;
    }

}
