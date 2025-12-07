package com.bunary.vocab.learning.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.PageResponseDTO;
import com.bunary.vocab.learning.dto.response.UserWsRecentResDTO;
import com.bunary.vocab.learning.service.IUserWsRecentSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user-wordset-recents")
public class UserWsRecentController {
        private final IUserWsRecentSvc userWsRecentSvc;

        // ---> Đã batch method vô api khác rồi!
        // @PostMapping("/record")
        // public ResponseEntity<SuccessReponseDTO> record(@RequestBody
        // UserWsRecentReqDTO dto) {

        // this.userWsRecentSvc.record(dto.getWordSetId());

        // return ResponseEntity.ok()
        // .body(SuccessReponseDTO.builder()
        // .statusCode(200)
        // .message("successfully")
        // .build());
        // }

        @GetMapping("/self")
        public ResponseEntity<SuccessReponseDTO> findAllByCurrentUser(
                        @RequestParam(required = false) Map<String, String> params,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size) {

                Page<UserWsRecentResDTO> result = this.userWsRecentSvc.findAllByCurrentUser(params,
                                PageRequest.of(page, size));

                return ResponseEntity.ok()
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Successfully")
                                                .data(result.getContent())
                                                .pagination(new PageResponseDTO(result))
                                                .build());
        }
}
