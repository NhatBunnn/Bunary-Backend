package com.bunary.vocab.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.service.wordSet.IWordSetService;
import com.bunary.vocab.service.wordSetStat.IWordSetStatService;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wordsets")
public class WordSetStatController {
    private final IWordSetService wordSetService;
    private final IWordSetStatService wordSetStatService;

    @GetMapping("/wordsets/stats")
    public ResponseEntity<?> findAllWordSetStat(
            @RequestParam(required = false) String include,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

        Pageable pageable = PageableUtil.createPageable(page, size, sort);

        boolean includeUser = include != null && include.contains("user");

        Page<WordSetReponseDTO> result = null;
        if (includeUser) {
            result = this.wordSetService.findAllByVisibilityWithUser("PUBLIC", pageable);
        }

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("WordSets retrieved successfully")
                        .data(result.getContent())
                        .pagination(new PageResponseDTO(result))
                        .build());
    }
}
