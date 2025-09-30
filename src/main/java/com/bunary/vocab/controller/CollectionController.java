package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.CollectionResDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.dto.request.CollectionReqDTO;
import com.bunary.vocab.service.collection.CollectionService;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CollectionController {
    private final CollectionService collectionService;

    @PostMapping("/collection")
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
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageableUtil.createPageable(page, size, sort);

        Page<CollectionResDTO> result = this.collectionService.findAll(pageable);

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

    // @GetMapping("/collection/{collectionId}")
    // public ResponseEntity<?> getCollection(@PathVariable Long collectionId) {
    // CollectionResDTO result =
    // collectionService.getCollectionWithWordSets(collectionId);
    // return ResponseEntity.ok()
    // .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Tạo bộ sưu
    // tập thành công"), result));
    // }

}
