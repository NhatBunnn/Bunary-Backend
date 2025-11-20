package com.bunary.vocab.service.nosql.ChatMessage;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.ChatMessageResDTO;
import com.bunary.vocab.model.nosql.ChatMessage;
import com.bunary.vocab.repository.nosql.ChatMessageRepository;
import com.bunary.vocab.security.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChatMessageService implements IChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final SecurityUtil securityUtil;

    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(Instant.now());
        return chatMessageRepository.save(message);
    }

    @Override
    public void sendMessage(ChatMessageResDTO messageDto) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageContent(messageDto.getMessageContent());
        chatMessage.setReceiverId(messageDto.getReceiverId());
        chatMessage.setSenderId(messageDto.getSenderId());
        chatMessage.setTimestamp(Instant.now());

        chatMessageRepository.save(chatMessage);

        // send realtime messages
        messagingTemplate.convertAndSendToUser(
                messageDto.getReceiverId(),
                "/queue/messages",
                messageDto);
    }

    public Page<ChatMessageResDTO> findAllChatMessages(String receiverId, Pageable pageable) {

        String senderId = securityUtil.getCurrentUser()
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        Page<ChatMessage> chatMessagePage = this.chatMessageRepository.findChatBetween(
                senderId, receiverId,
                pageable);

        List<ChatMessageResDTO> chatMessageList = chatMessagePage.stream().map((msg) -> {
            ChatMessageResDTO chatMessageResDTO = ChatMessageResDTO.builder()
                    .messageContent(msg.getMessageContent())
                    .senderId(msg.getSenderId())
                    .receiverId(msg.getReceiverId())
                    .timestamp(msg.getTimestamp())
                    .build();

            return chatMessageResDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(chatMessageList, pageable, chatMessagePage.getTotalElements());
    }
}
