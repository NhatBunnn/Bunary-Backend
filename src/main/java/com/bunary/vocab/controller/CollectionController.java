package com.bunary.vocab.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.reponse.WordSetReponseDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
import com.bunary.vocab.service.collection.ICollectionService;
import com.bunary.vocab.service.wordSet.IWordSetService;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CollectionController {
        private final ICollectionService collectionService;
        private final IWordSetService wordSetService;

        @PostMapping("/collections")
        public ResponseEntity<?> create(@RequestBody CollectionReqDTO collection) throws Exception {

                CollectionResDTO result = this.collectionService.create(collection);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Collection created successfully")
                                                .data(result)
                                                .build());
        }

        @GetMapping("/collections")
        public ResponseEntity<?> findAllWithUser(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "id,asc") String[] sort) {

                Pageable pageable = PageableUtil.createPageable(page, size, sort);

                Page<CollectionResDTO> result = this.collectionService.findAllWithUser(pageable);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Fetched all collections successfully")
                                                .data(result)
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }

        @PostMapping("/collections/{collectionId}/wordsets/{wordSetId}")
        public ResponseEntity<?> addWordSetToCollection(@PathVariable Long collectionId,
                        @PathVariable Long wordSetId) throws Exception {

                this.collectionService.addWordSetToCollection(collectionId, wordSetId);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("WordSet added to collection successfully")
                                                .build());
        }

        @PostMapping("/collections/{collectionId}/wordsets")
        public ResponseEntity<?> getWordsetsByCollectionId(@PathVariable Long collectionId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "id,asc") String[] sort) {

                Pageable pageable = PageableUtil.createPageable(page, size, sort);

                Page<WordSetReponseDTO> result = this.wordSetService.findAllByCollectionId(collectionId, pageable);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("Colections retrieved successfully")
                                                .data(result.getContent())
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }

        @PostMapping("/collections/{collectionId}")
        public ResponseEntity<?> findById(@PathVariable Long collectionId) {

                CollectionResDTO result = this.collectionService.findById(collectionId);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(201)
                                                .message("Colections retrieved successfully")
                                                .data(result)
                                                .build());
        }

}
