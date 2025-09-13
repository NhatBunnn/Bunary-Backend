package com.bunary.vocab.exception;

import java.util.List;

import lombok.Getter;

/*
 * ResourceNotFoundException: khi không tìm thấy dữ liệu (user, bài viết…)
 * BadRequestException: dữ liệu gửi lên không hợp lệ (validate input)
 * ConflictException: xung đột dữ liệu (ví dụ tạo user trùng email)
 * UnauthorizedException: chưa đăng nhập hoặc token không hợp lệ
 * ForbiddenException: đã đăng nhập nhưng không đủ quyền truy cập
 * InternalServerException: lỗi hệ thống, lỗi server không dự đoán trước
 * BadCredentialsException
 */

public class CustomException {

    @Getter
    public static class ErrorCodeException extends RuntimeException {
        private final List<FieldErrorCode> fieldErrorCode;
        private final List<GlobalErrorCode> globalErrorCode;

        public ErrorCodeException(FieldErrorCode fieldErrorCode) {
            super();
            this.fieldErrorCode = List.of(fieldErrorCode);
            this.globalErrorCode = null;
        }

        public ErrorCodeException(GlobalErrorCode globalErrorCode) {
            super();
            this.fieldErrorCode = null;
            this.globalErrorCode = List.of(globalErrorCode);
        }

        public ErrorCodeException(List<FieldErrorCode> fieldErrorCode, List<GlobalErrorCode> globalErrorCode) {
            super();
            this.fieldErrorCode = fieldErrorCode;
            this.globalErrorCode = globalErrorCode;
        }
    }

    @Getter
    public static class ResourceNotFoundException extends ErrorCodeException {
        public ResourceNotFoundException(FieldErrorCode fieldErrorCode) {
            super(fieldErrorCode);
        }

        public ResourceNotFoundException(GlobalErrorCode globalErrorCode) {
            super(globalErrorCode);
        }

        public ResourceNotFoundException(List<FieldErrorCode> fieldErrorCode, List<GlobalErrorCode> globalErrorCode) {
            super(fieldErrorCode, globalErrorCode);
        }
    }

    @Getter
    public static class BadRequestException extends ErrorCodeException {
        public BadRequestException(FieldErrorCode fieldErrorCode) {
            super(fieldErrorCode);
        }

        public BadRequestException(GlobalErrorCode globalErrorCode) {
            super(globalErrorCode);
        }

        public BadRequestException(List<FieldErrorCode> fieldErrorCode, List<GlobalErrorCode> globalErrorCode) {
            super(fieldErrorCode, globalErrorCode);
        }
    }

    @Getter
    public static class ConflictException extends ErrorCodeException {
        public ConflictException(FieldErrorCode fieldErrorCode) {
            super(fieldErrorCode);
        }

        public ConflictException(GlobalErrorCode globalErrorCode) {
            super(globalErrorCode);
        }

        public ConflictException(List<FieldErrorCode> fieldErrorCode, List<GlobalErrorCode> globalErrorCode) {
            super(fieldErrorCode, globalErrorCode);
        }
    }

    @Getter
    public static class UnauthorizedException extends ErrorCodeException {
        public UnauthorizedException(FieldErrorCode fieldErrorCode) {
            super(fieldErrorCode);
        }

        public UnauthorizedException(GlobalErrorCode globalErrorCode) {
            super(globalErrorCode);
        }

        public UnauthorizedException(List<FieldErrorCode> fieldErrorCode, List<GlobalErrorCode> globalErrorCode) {
            super(fieldErrorCode, globalErrorCode);
        }
    }

    @Getter
    public static class ForbiddenException extends ErrorCodeException {
        public ForbiddenException(FieldErrorCode fieldErrorCode) {
            super(fieldErrorCode);
        }

        public ForbiddenException(GlobalErrorCode globalErrorCode) {
            super(globalErrorCode);
        }

        public ForbiddenException(List<FieldErrorCode> fieldErrorCode, List<GlobalErrorCode> globalErrorCode) {
            super(fieldErrorCode, globalErrorCode);
        }
    }

    @Getter
    public static class BadCredentialsException extends ErrorCodeException {
        public BadCredentialsException(FieldErrorCode fieldErrorCode) {
            super(fieldErrorCode);
        }

        public BadCredentialsException(GlobalErrorCode globalErrorCode) {
            super(globalErrorCode);
        }

        public BadCredentialsException(List<FieldErrorCode> fieldErrorCode, List<GlobalErrorCode> globalErrorCode) {
            super(fieldErrorCode, globalErrorCode);
        }
    }

    // lỗi hệ thống, lỗi server
    public static class InternalServerException extends RuntimeException {
        public InternalServerException(String message) {
            super(message);
        }
    }
}
