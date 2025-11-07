// File: ChatMessageController.java → CHỈ DÀNH CHO REST API
package com.bunary.vocab.controller;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.ChatMessageResDTO;
import com.bunary.vocab.dto.reponse.ChatMessageResponseDTO;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.ChatMessage.IChatMessageService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChatMessageController {
        private final SimpMessagingTemplate messagingTemplate;
        private final IChatMessageService chatMessageService;
        private final SecurityUtil securityUtil;

        @MessageMapping("/chat.send")
        public void sendMessage(
                        @Payload ChatMessageResDTO message,
                        Principal principal) {

                message.setSenderId(principal.getName());

                messagingTemplate.convertAndSendToUser(
                                message.getReceiverId(),
                                "/queue/messages",
                                message);

        }

        @GetMapping("/chatmessages/{receiverId}")
        public ResponseEntity<?> getChatMessageByReceiverId(@PathVariable String receiverId) {
                List<ChatMessageResponseDTO> result = chatMessageService.findBySenderIdAndReceiverId(
                                UUID.fromString(securityUtil.getCurrentUser().get()),
                                UUID.fromString(receiverId));

                return ResponseEntity.ok(SuccessReponseDTO.builder()
                                .statusCode(200)
                                .message("Fetched messages successfully")
                                .data(result)
                                .build());
        }
}