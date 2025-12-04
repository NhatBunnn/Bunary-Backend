package com.bunary.vocab.batchapi.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.batchapi.dto.request.FinishWordSetReqDTO;
import com.bunary.vocab.batchapi.service.IFinishWordSetSvc;
import com.bunary.vocab.dto.SuccessReponseDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FinishWordSetController {
    private final IFinishWordSetSvc finishWordSetSvc;

    // thêm mode ( ví dụ: flashcard, test vào body sau)
    @PostMapping("/wordsets/{wordSetId}/finish")
    public ResponseEntity<?> finish(
            @PathVariable Long wordSetId,
            @RequestBody FinishWordSetReqDTO request) throws Exception {

        this.finishWordSetSvc.finish(wordSetId, request);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Successfully")
                        .build());
    }

}
