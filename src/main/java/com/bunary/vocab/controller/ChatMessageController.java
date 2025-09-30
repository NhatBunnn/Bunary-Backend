package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.ChatMessageResponseDTO;
import com.bunary.vocab.security.SecurityUtil;
import com.bunary.vocab.service.ChatMessage.IChatMessageService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ChatMessageController {
    private final IChatMessageService chatMessageService;
    private final SecurityUtil securityUtil;

    @GetMapping("/chatmessages/{receiverId}")
    public ResponseEntity<?> getChatMessageByReceiverId(@PathVariable String receiverId) throws Exception {
        List<ChatMessageResponseDTO> result = this.chatMessageService.findBySenderIdAndReceiverId(
                UUID.fromString(this.securityUtil.getCurrentUser().get()),
                UUID.fromString(receiverId));

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Fetched messages successfully")
                        .data(result)
                        .build());
    }
}
