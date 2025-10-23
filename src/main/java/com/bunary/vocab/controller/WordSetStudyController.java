package com.bunary.vocab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.WordSetStudyResDTO;
import com.bunary.vocab.service.wordSetStudy.IWordSetStudyService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wordsets")
public class WordSetStudyController {
    final private IWordSetStudyService wordSetStudyService;

    @PostMapping("{wordSetId}/study")
    public ResponseEntity<?> createOrUpdate(
            @PathVariable Long wordSetId,
            @PathVariable Long studyId) {

        WordSetStudyResDTO result = this.wordSetStudyService.createOrUpdate(wordSetId);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Rating created successfully")
                        .data(result)
                        .build());
    }
}
