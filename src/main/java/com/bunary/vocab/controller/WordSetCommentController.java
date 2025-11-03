package com.bunary.vocab.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetCommentResDTO;
import com.bunary.vocab.dto.request.WordSetCommentReqDTO;
import com.bunary.vocab.service.WordSetComment.IWordSetCommentService;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class WordSetCommentController {
    private final IWordSetCommentService wordSetCommentService;

    @PostMapping("/wordsets/{wordSetId}/comments")
    public ResponseEntity<?> createComment(@PathVariable Long wordSetId, @RequestBody WordSetCommentReqDTO wDto)
            throws Exception {

        WordSetCommentResDTO result = this.wordSetCommentService.createComment(wDto, wordSetId);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("Successfully")
                        .data(result)
                        .build());
    }

    @GetMapping("/wordsets/{wordSetId}/comments")
    public ResponseEntity<?> findAllByWordSetId(
            @PathVariable Long wordSetId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort)
            throws Exception {

        Pageable pageable = PageableUtil.createPageable(page, size, sort);

        Page<WordSetCommentResDTO> result = this.wordSetCommentService.findAllByWordSetId(wordSetId, pageable);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(201)
                        .message("Successfully")
                        .data(result.getContent())
                        .pagination(new PageResponseDTO(result))
                        .build());
    }

}
