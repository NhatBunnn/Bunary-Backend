package com.bunary.vocab.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // Common
    ID_NOT_FOUND(404, "ID_NOT_FOUND", "not found with id"),
    NOT_FOUND(404, "NOT_FOUND", "not found"),
    ALREADY_EXISTS(409, "ALREADY_EXISTS", "already exists"),
    FORBIDDEN(403, "FORBIDDEN", "access denied"),
    BAD_REQUEST(400, "BAD_REQUEST", "invalid request data"),
    INVALID_REQUEST(400, "INVALID_REQUEST", "Invalid request parameters"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "internal server error"),

    // User
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "User not found"),
    USER_NOT_VERIFIED(403, "AUTH_NOT_VERIFIED", "Account not verified"),

    // Auth
    AUTH_INVALID(401, "AUTH_INVALID", "Invalid authentication"),
    AUTH_EXPIRED(401, "AUTH_EXPIRED", "Authentication expired"),
    AUTH_SESSION_INVALID(401, "AUTH_SESSION_INVALID", "Invalid session"),
    AUTH_SESSION_EXPIRED(401, "AUTH_SESSION_EXPIRED", "Session expired"),

    // Collection
    COLLECTION_NAME_NOT_BLANK(400, "COLLECTION__NAME_NOT_BLANK", "Tên bộ sưu tập không được để trống"),
    COLLECTION_NAME_TOO_LONG(400, "COLLECTION_NAME_TOO_LONG", "Tên bộ sưu tập không được vượt quá 100 ký tự"),
    COLLECTION_NOT_FOUND(404, "COLLECTION_NOT_FOUND", "Collection not found"),

    // Rating
    RATING_NOT_FOUND(404, "RATING_NOT_FOUND", "Rating not found"),
    RATING_ALREADY_EXISTS(409, "RATING_ALREADY_EXISTS", "User has already rated this wordset"),

    // Follow
    CANNOT_FOLLOW_YOURSELF(400, "CANNOT_FOLLOW_YOURSELF", "You cannot follow yourself"),

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
