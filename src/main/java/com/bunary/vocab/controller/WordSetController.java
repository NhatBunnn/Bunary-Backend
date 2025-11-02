package com.bunary.vocab.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.WordSetRequestDTO;
import com.bunary.vocab.service.word.IWordService;
import com.bunary.vocab.service.wordSet.IWordSetService;
import com.bunary.vocab.util.PageableUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WordSetController {
        private final IWordSetService wordSetService;
        private final IWordService wordService;

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

        @PutMapping("/wordsets/{wordSetId}")
        public ResponseEntity<?> update(@RequestPart("wordSet") String wordSetString, @PathVariable Long wordSetId,
                        @RequestPart(value = "thumbnailFile", required = false) MultipartFile file) throws Exception {

                ObjectMapper mapper = new ObjectMapper();
                WordSetRequestDTO wordSet = mapper.readValue(wordSetString, WordSetRequestDTO.class);

                WordSetReponseDTO result = this.wordSetService.update(wordSet, wordSetId, file);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSet created successfully")
                                                .data(result)
                                                .build());
        }

        @GetMapping("/wordsets")
        public ResponseEntity<?> findAllWordSets(
                        @RequestParam(required = false) Map<String, String> params,

                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size) throws Exception {

                Page<WordSetReponseDTO> result = this.wordSetService.findAll(params, PageRequest.of(page, size));

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSets retrieved successfully")
                                                .data(result.getContent())
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }

        @GetMapping("/wordsets/history/me")
        public ResponseEntity<?> findAllMyRecentWordSets(
                        @RequestParam(required = false) Map<String, String> params,

                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size) throws Exception {

                Page<WordSetReponseDTO> result = this.wordSetService.findAllMyRecentWordSets(params,
                                PageRequest.of(page, size));

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSets retrieved successfully")
                                                .data(result.getContent())
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }

        @GetMapping("/wordsets/me")
        public ResponseEntity<?> findAllByCurrentUser(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "id,asc") String[] sort) {
                Pageable pageable = PageableUtil.createPageable(page, size, sort);

                Page<WordSetReponseDTO> result = this.wordSetService.findAllByCurrentUser(pageable);
                this.wordSetService.findAllByCurrentUser(pageable);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSets retrieved successfully")
                                                .data(result.getContent())
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }

        @GetMapping("/wordsets/{wordSetId}")
        public ResponseEntity<?> findByWordSetId(@PathVariable Long wordSetId) throws Exception {

                WordSetReponseDTO result = this.wordSetService.findById(wordSetId);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("WordSets retrieved successfully")
                                                .data(result)
                                                .build());
        }

        @DeleteMapping("/wordsets/{wordSetId}")
        public ResponseEntity<?> removeCollection(@PathVariable Long wordSetId) {
                this.wordSetService.removeWordSet(wordSetId);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("Wordset deleted successfully")
                                                .build());
        }

}
