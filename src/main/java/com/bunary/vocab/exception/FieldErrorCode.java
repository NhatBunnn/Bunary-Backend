package com.bunary.vocab.exception;

public enum FieldErrorCode {
    EMAIL_EXISTS("email", "Email này đã tồn tại"),
    BAD_CREDENTIALS("credential", "Tên đăng nhập hoặc mật khẩu không đúng");

    private final String field;
    private final String message;

    FieldErrorCode(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
