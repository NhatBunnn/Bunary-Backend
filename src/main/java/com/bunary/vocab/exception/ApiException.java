package com.bunary.vocab.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.bunary.vocab.code.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * ResourceNotFoundException: khi không tìm thấy dữ liệu (user, bài viết…)
 * BadRequestException: dữ liệu gửi lên không hợp lệ (validate input)
 * ConflictException: xung đột dữ liệu (ví dụ tạo user trùng email)
 * UnauthorizedException: chưa đăng nhập hoặc token không hợp lệ
 * ForbiddenException: đã đăng nhập nhưng không đủ quyền truy cập
 * InternalServerException: lỗi hệ thống, lỗi server không dự đoán trước
 * BadCredentialsException
 */

@Getter
@Setter
public class ApiException extends RuntimeException {
    private final int code;
    private final String errorCode;
    private final String message;

    public ApiException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }
}
