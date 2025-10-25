package com.bunary.vocab.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.service.wordSetStat.IWordSetStatService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wordsets")
public class WordSetStatController {

    private final IWordSetStatService wordSetStatService;

    @GetMapping("/stats")
    public ResponseEntity<?> findAllWordSets(
            @RequestParam(required = false) Map<String, String> params,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    // @RequestParam(defaultValue = "id,asc") String[] sort
    ) throws Exception {

        Page<WordSetReponseDTO> result = this.wordSetStatService.findAll(params, PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("WordSets retrieved successfully")
                        .data(result.getContent())
                        .pagination(new PageResponseDTO(result))
                        .build());
    }

}
