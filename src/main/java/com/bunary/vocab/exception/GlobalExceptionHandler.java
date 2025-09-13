package com.bunary.vocab.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bunary.vocab.dto.FailureResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<?> buildResponseEntity(HttpStatus status, List<FieldErrorCode> fieldErrorCode,
            List<GlobalErrorCode> globalErrorCode) {

        FailureResponseDTO response = new FailureResponseDTO();
        response.setStatusCode(status.value());

        // xử lý global errors
        if (globalErrorCode != null && !globalErrorCode.isEmpty()) {
            List<String> messages = globalErrorCode.stream()
                    .map(GlobalErrorCode::getMessage)
                    .toList();
            response.setError(messages); // DTO nhận List<String>
        }

        // xử lý field errors
        if (fieldErrorCode != null && !fieldErrorCode.isEmpty()) {
            Map<String, String> fieldErrors = fieldErrorCode.stream()
                    .collect(Collectors.toMap(
                            FieldErrorCode::getField,
                            FieldErrorCode::getMessage));
            response.setFieldError(fieldErrors);
        }

        return ResponseEntity.status(status.value()).body(response);
    }

    @ExceptionHandler(CustomException.ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(CustomException.ResourceNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getFieldErrorCode(), ex.getGlobalErrorCode());
    }

    @ExceptionHandler(CustomException.BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(CustomException.BadRequestException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getFieldErrorCode(), ex.getGlobalErrorCode());
    }

    @ExceptionHandler(CustomException.ConflictException.class)
    public ResponseEntity<?> handleConflictException(CustomException.ConflictException ex) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getFieldErrorCode(), ex.getGlobalErrorCode());
    }

    @ExceptionHandler(CustomException.UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(CustomException.UnauthorizedException ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getFieldErrorCode(), ex.getGlobalErrorCode());
    }

    @ExceptionHandler(CustomException.ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(CustomException.ForbiddenException ex) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getFieldErrorCode(), ex.getGlobalErrorCode());
    }

    @ExceptionHandler(CustomException.BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(CustomException.BadCredentialsException ex) {
        return buildResponseEntity(HttpStatus.FORBIDDEN, ex.getFieldErrorCode(), ex.getGlobalErrorCode());
    }

    @ExceptionHandler(CustomException.InternalServerException.class)
    public ResponseEntity<?> handleInternalServerException(CustomException.InternalServerException ex) {
        FailureResponseDTO response = new FailureResponseDTO();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // Optional: bắt tất cả exception khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        FailureResponseDTO response = new FailureResponseDTO();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
