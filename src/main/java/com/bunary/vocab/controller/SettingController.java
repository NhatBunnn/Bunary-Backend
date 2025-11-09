package com.bunary.vocab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
        private final ISettingService settingService;

        // @PostMapping("/settings")
        public ResponseEntity<?> create(@Valid @RequestBody SettingReqDTO settingReqDTO) throws Exception {

                SettingResDTO result = this.settingService.save(settingReqDTO);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Learningsetting created successfully")
                                                .data(result)
                                                .build());
        }

        @PutMapping("/settings")
        public ResponseEntity<?> update(@Valid @RequestBody SettingReqDTO settingReqDTO) throws Exception {

                SettingResDTO result = this.settingService.update(settingReqDTO);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Learningsetting created successfully")
                                                .data(result)
                                                .build());
        }

        @GetMapping("/settings/{type}")
        public ResponseEntity<?> findByTypeAndCurrentUser(@PathVariable String type)
                        throws Exception {

                SettingResDTO result = this.settingService.findByTypeAndCurrentUser(type);

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Learningsetting created successfully")
                                                .data(result)
                                                .build());
        }

}
