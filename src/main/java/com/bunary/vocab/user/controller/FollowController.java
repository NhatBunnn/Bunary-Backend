package com.bunary.vocab.user.controller;

import java.util.Map;
import java.util.UUID;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.user.dto.response.FollowResDTO;
import com.bunary.vocab.user.service.IFollowSvc;
import com.bunary.vocab.util.PageableUtil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {
        private final IFollowSvc followSvc;

        @PostMapping("/users/{followeeId}/follow")
        public ResponseEntity<SuccessReponseDTO> toggleFollow(@PathVariable UUID followeeId) throws Exception {
                this.followSvc.toggleFollow(followeeId);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Successfully")
                                                .build());
        }

        @GetMapping("/users/me/followers")
        public ResponseEntity<SuccessReponseDTO> findAllFollowerByCurrentUser(
                        @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(defaultValue = "id,asc") String[] sort) throws Exception {

                Pageable pageable = PageableUtil.createPageable(page, size, sort);

                Page<FollowResDTO> result = this.followSvc.findAllFollowerByCurrentUser(keyword, pageable);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Successfully")
                                                .data(result.getContent())
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }
}
