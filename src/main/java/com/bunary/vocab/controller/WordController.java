package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.service.word.IWordService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WordController {
    private final IWordService wordService;

    @GetMapping("/words/{wordSetId}")
    public ResponseEntity<?> findByWordSetId(@PathVariable Long wordSetId) throws Exception {

        List<WordReponseDTO> result = this.wordService.findByWordSetId(wordSetId);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Words retrieved successfully")
                        .data(result)
                        .build());
    }

}
