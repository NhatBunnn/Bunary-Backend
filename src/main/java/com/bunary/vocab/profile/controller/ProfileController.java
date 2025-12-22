package com.bunary.vocab.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.profile.dto.request.ProfileReqDTO;
import com.bunary.vocab.profile.dto.response.ProfileResDTO;
import com.bunary.vocab.profile.service.IProfileSvc;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController {
    private final IProfileSvc profileSvc;

    @PutMapping("/profiles")
    public ResponseEntity<?> findByIdWithRoles(@RequestBody ProfileReqDTO profileReqDTO) throws Exception {

        ProfileResDTO result = this.profileSvc.update(profileReqDTO);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Successfully")
                        .data(result)
                        .build());
    }
}
