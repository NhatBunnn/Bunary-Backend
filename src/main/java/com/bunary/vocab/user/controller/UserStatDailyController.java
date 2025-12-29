package com.bunary.vocab.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.user.dto.response.UserStatDailyResDTO;
import com.bunary.vocab.user.dto.response.enums.StatsPeriodEnum;
import com.bunary.vocab.user.service.IUserStatDailySvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserStatDailyController {
    private final IUserStatDailySvc userWsDailySvc;

    @GetMapping("/users/me/stats-daily")
    public ResponseEntity<SuccessReponseDTO> findAllByCurrentUser(@RequestParam StatsPeriodEnum period) {

        UserStatDailyResDTO result = this.userWsDailySvc.findByPeriod(period);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Successfully")
                        .data(result)
                        .build());
    }

}
