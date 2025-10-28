package com.bunary.vocab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.service.wordSetStat.IWordSetStatService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wordset-stats")
public class WordSetStatController {

        private final IWordSetStatService wordSetStatService;

        @PostMapping("/{wordSetId}/action/view")
        public ResponseEntity<?> increaseView(@PathVariable Long wordSetId) {
                wordSetStatService.increaseView(wordSetId);

                return ResponseEntity.ok(
                                SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("View count increased successfully")
                                                .data(null)
                                                .build());
        }

        @PostMapping("/{wordSetId}/action/study")
        public ResponseEntity<?> increaseStudy(@PathVariable Long wordSetId) {
                wordSetStatService.increaseStudy(wordSetId);

                return ResponseEntity.ok(
                                SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Study count increased successfully")
                                                .data(null)
                                                .build());
        }

}
