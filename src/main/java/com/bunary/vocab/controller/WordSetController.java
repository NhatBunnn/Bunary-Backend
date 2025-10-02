package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.service.wordSet.IWordSetService;
import com.bunary.vocab.util.PageableUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WordSetController {
        private final IWordSetService wordSetService;

        @PostMapping("/wordsets")
        public ResponseEntity<?> createWordSet(@RequestPart("wordSet") String wordSetString,
                        @RequestPart(value = "thumbnailFile", required = false) MultipartFile file) throws Exception {

                ObjectMapper mapper = new ObjectMapper();
                WordSetRequestDTO wordSet = mapper.readValue(wordSetString, WordSetRequestDTO.class);

                WordSetReponseDTO result = this.wordSetService.createWordSet(wordSet, file);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSet created successfully")
                                                .data(result)
                                                .build());
        }

        @GetMapping("/wordsets")
        public ResponseEntity<?> getAllWordSet(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

                Pageable pageable = PageableUtil.createPageable(page, size, sort);

                Page<WordSetReponseDTO> result = this.wordSetService.findAllWithAuthor(pageable);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSets retrieved successfully")
                                                .data(result.getContent())
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }

        @GetMapping("/wordsets/{wordSetId}")
        public ResponseEntity<?> findByWordSetId(@PathVariable Long wordSetId,
                        @RequestParam(value = "include", required = false) String include) throws Exception {

                boolean includeUser = include != null && include.contains("user");
                boolean includeCollection = include != null && include.contains("collection");

                WordSetReponseDTO result = new WordSetReponseDTO();

                if (includeUser && includeCollection) {
                        result = this.wordSetService.findByIdWithUserAndCollection(wordSetId);
                } else {
                        result = this.wordSetService.findById(wordSetId);
                }

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSets retrieved successfully")
                                                .data(result)
                                                .build());
        }

}
