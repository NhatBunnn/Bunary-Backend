package com.bunary.vocab.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bunary.vocab.dto.FailureResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailureResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String errorCode = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");

        FailureResponseDTO response = new FailureResponseDTO();
        response.setStatusCode(400);
        response.setErrorCode(errorCode);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<FailureResponseDTO> handleApiException(ApiException ex) {
        FailureResponseDTO response = new FailureResponseDTO();
        response.setStatusCode(ex.getStatusCode());
        response.setErrorCode(ex.getErrorCode());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        // Vấn đề là những lỗi phát sinh tự động này sẽ để lộ thông tin nhạy cảm
        // => nên hash thông tin 500 + in lỗi ra console cho dev biết!
        ex.printStackTrace();

        FailureResponseDTO response = new FailureResponseDTO();
        response.setStatusCode(500);
        response.setErrorCode("INTERNAL_ERROR");
        return ResponseEntity.status(500).body(response);
    }

}
