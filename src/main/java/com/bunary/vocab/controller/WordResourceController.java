package com.bunary.vocab.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.WordResourceResDTO;
import com.bunary.vocab.service.term.IWordResourceService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WordResourceController {
    private final IWordResourceService wordResourceService;

    @GetMapping("/wordresources/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {

        List<WordResourceResDTO> result = this.wordResourceService.search(keyword);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("Successfully")
                        .data(result)
                        .build());
    }

}
