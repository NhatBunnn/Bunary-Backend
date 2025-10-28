package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.WordReponseDTO;
import com.bunary.vocab.service.word.IWordService;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WordController {
    private final IWordService wordService;

    @GetMapping("/words/{wordSetId}")
    public ResponseEntity<?> findWordsByWordSetId(@PathVariable Long wordSetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

        Pageable pageable = PageableUtil.createPageable(page, size, sort);

        Page<WordReponseDTO> result = this.wordService.findByWordSetId(wordSetId, pageable);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Words retrieved successfully")
                        .data(result.getContent())
                        .pagination(new PageResponseDTO(result))
                        .build());
    }
}
