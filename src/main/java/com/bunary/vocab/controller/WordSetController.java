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
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Tạo bộ từ vựng thành công"), result));
    }

    @GetMapping("/wordsets")
    public ResponseEntity<?> getAllWordSet(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

        String sortField = sort[0];
        Sort.Direction direction;

        if (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        Page<WordSetReponseDTO> result = this.wordSetService.findAllWithAuthor(pageable);

        PageResponseDTO pageResponseDTO = new PageResponseDTO(result);

        return ResponseEntity.ok()
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Success"), result.getContent(),
                        pageResponseDTO));
    }

    @GetMapping("/wordsets/{wordSetId}")
    public ResponseEntity<?> findByWordSetId(@PathVariable Long wordSetId) throws Exception {

        WordSetReponseDTO result = this.wordSetService.findById(wordSetId);

        return ResponseEntity.ok()
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Success"), result));
    }

    @PostMapping("/collections/{collectionId}/wordsets")
    public ResponseEntity<?> findByCollectionId(@PathVariable Long collectionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        String sortField = sort[0];
        Sort.Direction direction;

        if (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction,
                sortField));

        Page<WordSetReponseDTO> result = this.wordSetService.findAllByCollectionId(collectionId, pageable);

        PageResponseDTO pageResponseDTO = new PageResponseDTO(result);

        return ResponseEntity.ok()
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Success"),
                        result.getContent(),
                        pageResponseDTO));
    }

}
