package com.bunary.vocab.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.user.dto.request.UserWsProgressReqDTO;
import com.bunary.vocab.user.dto.response.UserWsProgressResDTO;
import com.bunary.vocab.user.service.IUserWsProgressSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserWsProgressController {
    private final IUserWsProgressSvc userWsProgressSvc;

    @PostMapping("/wordsets/{wordsetId}/user-progress")
    public ResponseEntity<?> findByUserId(
            @RequestBody UserWsProgressReqDTO userWsProgressReqDTO,
            @PathVariable("wordsetId") Long wordsetId) throws Exception {

        UserWsProgressResDTO result = this.userWsProgressSvc.record(wordsetId, userWsProgressReqDTO);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Data retrieved successfully")
                        .data(result)
                        .build());
    }
}
