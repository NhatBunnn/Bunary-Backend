package com.bunary.vocab.mapper;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bunary.vocab.dto.reponse.ChatMessageResponseDTO;
import com.bunary.vocab.model.ChatMessage;
import com.bunary.vocab.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChatMessageMapper {

    public ChatMessage convertToChatMessage(ChatMessageResponseDTO chatMsgDTO) {
        User sender = new User();
        sender.setId(chatMsgDTO.getSenderId());

        User receiver = new User();
        receiver.setId(chatMsgDTO.getReceiverId());

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageContent(chatMsgDTO.getMessageContent());
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setStatus(chatMsgDTO.getStatus());

        return chatMessage;
    }

    public ChatMessageResponseDTO convertToChatMessageResponseDTO(ChatMessage chatMessage) {
        ChatMessageResponseDTO chatMsgDTO = new ChatMessageResponseDTO();
        chatMsgDTO.setMessageContent(chatMessage.getMessageContent());
        chatMsgDTO.setSenderId(chatMessage.getSender().getId());
        chatMsgDTO.setReceiverId(chatMessage.getReceiver().getId());
        chatMsgDTO.setStatus(chatMessage.getStatus());

        return chatMsgDTO;
    }
}
