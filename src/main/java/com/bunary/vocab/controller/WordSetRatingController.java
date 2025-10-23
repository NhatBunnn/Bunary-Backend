package com.bunary.vocab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.WordSetRatingResDTO;
import com.bunary.vocab.dto.request.WordSetRatingReqDTO;
import com.bunary.vocab.service.WordSetRating.IWordSetRatingService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wordsets")
public class WordSetRatingController {
        private final IWordSetRatingService ratingService;

        @PostMapping("{wordSetId}/ratings")
        public ResponseEntity<?> createOrUpdateRating(@Valid @RequestBody WordSetRatingReqDTO ratingReqDTO,
                        @PathVariable long wordSetId) {

                WordSetRatingResDTO result = this.ratingService.createOrUpdateRating(ratingReqDTO, wordSetId);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Rating created successfully")
                                                .data(result)
                                                .build());
        }

        @PreAuthorize("hasRole('ADMIN') or @wordSetRatingService.isOwner(#ratingId)")
        @DeleteMapping("/ratings/{ratingId}")
        public ResponseEntity<?> delete(@PathVariable Long ratingId) {

                this.ratingService.delete(ratingId);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Rating created successfully")
                                                .build());
        }
}
