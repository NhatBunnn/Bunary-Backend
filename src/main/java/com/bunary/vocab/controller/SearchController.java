package com.bunary.vocab.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.SearchResDTO;
import com.bunary.vocab.service.searchService.ISearchService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SearchController {
    private final ISearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {

        SearchResDTO result = this.searchService.search(keyword, 3);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("Successfully")
                        .data(result)
                        .build());
    }
}
