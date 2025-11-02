package com.bunary.vocab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.UserWordsetHistoryResDTO;
import com.bunary.vocab.service.UserWordsetHistory.UserWordsetHistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wordsets")
public class UserWordsetHistoryController {
    private final UserWordsetHistoryService userWordsetHistoryService;

    @PostMapping("{wordSetId}/history")
    public ResponseEntity<?> recordWordSetStudy(@PathVariable Long wordSetId) {

        UserWordsetHistoryResDTO result = this.userWordsetHistoryService.recordWordSetStudy(wordSetId);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("successfully")
                        .data(result)
                        .build());
    }
}
