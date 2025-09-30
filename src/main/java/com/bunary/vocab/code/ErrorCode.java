package com.bunary.vocab.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // User
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "User not found"),
    USER_NOT_VERIFIED(403, "AUTH_NOT_VERIFIED", "Account not verified"),

    // Auth
    AUTH_INVALID(401, "AUTH_INVALID", "Invalid authentication"),
    AUTH_EXPIRED(401, "AUTH_EXPIRED", "Authentication expired"),
    AUTH_SESSION_INVALID(401, "AUTH_SESSION_INVALID", "Invalid session"),
    AUTH_SESSION_EXPIRED(401, "AUTH_SESSION_EXPIRED", "Session expired"),

    // Email
    EMAIL_EXISTS(409, "EMAIL_EXISTS", "Email already exists"),
    EMAIL_INVALID(400, "EMAIL_INVALID", "Email format is invalid"),
    EMAIL_CODE_INVALID(401, "EMAIL_CODE_INVALID", "Email verification code is invalid"),
    EMAIL_CODE_EXPIRED(401, "EMAIL_CODE_EXPIRED", "Email verification code expired"),
    EMAIL_CODE_ATTEMPTS_EXCEEDED(429, "EMAIL_CODE_ATTEMPTS_EXCEEDED", "Too many verification attempts"),
    EMAIL_CODE_USED(400, "EMAIL_CODE_USED", "Email verification code already used"),
    EMAIL_SEND_FAILED(500, "EMAIL_SEND_FAILED", "Failed to send email");

    private final int code;
    private final String errorCode;
    private final String message;
}
