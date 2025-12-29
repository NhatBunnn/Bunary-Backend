package com.bunary.vocab.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.service.IUserStatsSvc;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserStatsController {
    private final IUserStatsSvc userStatsSvc;

    @GetMapping("/users/me/stats")
    public ResponseEntity<?> findByUserId() throws Exception {
        UserStatsResDTO result = this.userStatsSvc.findByCurrentUser();

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Data retrieved successfully")
                        .data(result)
                        .build());
    }

}
