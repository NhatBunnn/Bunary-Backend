package com.bunary.vocab.learning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.learning.dto.response.UserWsDailyResDTO;
import com.bunary.vocab.learning.service.IUserWsDailySvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user-wordset-daily")
public class UserWsDailyController {
    private final IUserWsDailySvc userWsDailySvc;

    @GetMapping("/self")
    public ResponseEntity<SuccessReponseDTO> findAllByCurrentUser() {

        List<UserWsDailyResDTO> result = this.userWsDailySvc.findLast30Days();

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Successfully")
                        .data(result)
                        .build());
    }

}
