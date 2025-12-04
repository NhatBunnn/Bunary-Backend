package com.bunary.vocab.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.UserResponseDTO;
import com.bunary.vocab.user.dto.response.UserStatsResDTO;
import com.bunary.vocab.user.service.IUserStatsSvc;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserStatsController {
    private final IUserStatsSvc userStatsSvc;

    @GetMapping("/users/{userid}/stats")
    public ResponseEntity<?> findByUserId(@PathVariable String userid) throws Exception {
        UserStatsResDTO result = this.userStatsSvc.findByUserId(UUID.fromString(userid));

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Data retrieved successfully")
                        .data(result)
                        .build());
    }

}
