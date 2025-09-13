package com.bunary.vocab.exception;

public enum GlobalErrorCode {
    USER_NOT_FOUND("Không tìm thấy tài khoản"),
    BAD_CREDENTIALS("Tên đăng nhập hoặc mật khẩu không đúng"),
    USER_INVALID("Tài khoản chưa xác thực"),
    ACCESS_DENIED("Access denied"),
    INTERNAL_ERROR("Something went wrong"),
    SESSION_INVALID("Phiên đăng nhập không hợp lệ"),
    SESSION_EXPIRED("Phiên đăng nhập đã hết hạn"),
    TOO_MANY_ATTEMPTS("Vượt quá số lần thử, vui lòng đợi thêm 30 phút trước khi thử lại!"),

    // Nhaầm đây đang bên trong exception
    ACCOUNT_CREATED("Tạo tài khoản thành công"),
    ACCOUNT_CREATION_FAILED("Tạo tài khoản thất bại"),

    CODE_IS_USED("Bạn không còn mã xác thực , vui lòng tạo mã mới"),
    CODE_IS_EXPIRED("Mã xác thực đã hết hạn"),
    CODE_INVALID("Mã xác thực không hợp lệ"),
    SEND_CODE_FAILED("Gửi mã thất bại");

    private final String message;

    GlobalErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
