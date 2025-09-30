package com.bunary.vocab.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bunary.vocab.dto.FailureResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<FailureResponseDTO> handleApiException(ApiException ex) {
        FailureResponseDTO response = new FailureResponseDTO();
        response.setCode(ex.getCode());
        response.setErrorCode(ex.getErrorCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(ex.getCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        // Vấn đề là những lỗi phát sinh tự động này sẽ để lộ thông tin nhạy cảm
        // => nên hash thông tin 500 + in lỗi ra console cho dev biết!
        ex.printStackTrace();

        FailureResponseDTO response = new FailureResponseDTO();
        response.setCode(500);
        response.setErrorCode("INTERNAL_ERROR");
        return ResponseEntity.status(500).body(response);
    }
}
