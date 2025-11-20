
package com.bunary.vocab.controller;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.ChatMessageResDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.service.nosql.ChatMessage.IChatMessageService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChatMessageController {
        private final IChatMessageService chatMessageService;

        @MessageMapping("/chat.send")
        public void sendMessage(
                        @Payload ChatMessageResDTO message,
                        Principal principal) {

                message.setSenderId(principal.getName());
                this.chatMessageService.sendMessage(message);
        }

        @GetMapping("/chatmessages/{receiverId}")
        public ResponseEntity<?> findAllChatMessages(
                        @PathVariable String receiverId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size) {

                Page<ChatMessageResDTO> result = this.chatMessageService.findAllChatMessages(receiverId,
                                PageRequest.of(page, size, Sort.by("timestamp").descending()));

                return ResponseEntity.ok(SuccessReponseDTO.builder()
                                .statusCode(200)
                                .message("Fetched messages successfully")
                                .data(result.getContent())
                                .pagination(new PageResponseDTO(result))
                                .build());
        }
}