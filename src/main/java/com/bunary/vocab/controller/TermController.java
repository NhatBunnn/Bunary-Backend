package com.bunary.vocab.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.TermResDTO;
import com.bunary.vocab.service.term.ITermService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TermController {
    private final ITermService termService;

    @GetMapping("/terms/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {

        List<TermResDTO> result = this.termService.search(keyword);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("Successfully")
                        .data(result)
                        .build());
    }

}
