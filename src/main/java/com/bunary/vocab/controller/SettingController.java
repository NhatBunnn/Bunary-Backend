package com.bunary.vocab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.SettingResDTO;
import com.bunary.vocab.dto.request.SettingReqDTO;
import com.bunary.vocab.service.setting.ISettingService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SettingController {
        private final ISettingService learningSettingService;

        @PostMapping("/settings/learning")
        public ResponseEntity<?> create(@Valid @RequestBody SettingReqDTO lSettingReqDTO) throws Exception {

                SettingResDTO result = this.learningSettingService.save(lSettingReqDTO);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Learningsetting created successfully")
                                                .data(result)
                                                .build());
        }

        @PostMapping("/settings/learning/{mode}")
        public ResponseEntity<?> findByModeForCurrentUser(@PathVariable String mode)
                        throws Exception {

                SettingResDTO result = this.learningSettingService.findLearningSettingByModeAndCurrentUser(mode);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Learningsetting created successfully")
                                                .data(result)
                                                .build());
        }

}
