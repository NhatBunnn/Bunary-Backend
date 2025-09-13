package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordRequestDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.model.Word;
import com.bunary.vocab.model.WordSet;
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
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Success"), result));
    }
}
