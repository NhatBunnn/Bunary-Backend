package com.bunary.vocab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.VerifyCodeReponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.exception.GlobalErrorCode;
import com.bunary.vocab.service.VerifyCode.IVerifyCodeService;
import com.bunary.vocab.service.authentication.IAuthService;

import lombok.AllArgsConstructor;

@RequestMapping("/api/v1")
@AllArgsConstructor
@RestController
public class AuthController {
    private final IAuthService authService;
    private final IVerifyCodeService verifyCodeService;

    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO user) throws Exception {
        boolean result = this.authService.register(user);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Registered successfully")
                        .data(result)
                        .build());
    }

    @PostMapping("/auth/verify-email")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeReponseDTO verifyCode) {
        AuthResponseDTO result = this.verifyCodeService.verifyCode(verifyCode);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Email verification successful")
                        .data(result)
                        .build());
    }

    @PostMapping("/auth/send-code")
    public ResponseEntity<?> resendCode(@RequestBody UserRequestDTO userReq) {
        boolean result = this.authService.sendCode(userReq);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Code sent successfully")
                        .data(result)
                        .build());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> Login(@RequestBody UserRequestDTO user) throws Exception {
        AuthResponseDTO result = this.authService.Login(user);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Logged in successfully")
                        .data(result)
                        .build());
    }

    @GetMapping("/auth/refresh-Token")
    public ResponseEntity<?> RefreshAccessToken(@CookieValue(name = "refresh_token") String refreshToken)
            throws Exception {
        AuthResponseDTO result = this.authService.RefreshAccessToken(refreshToken);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Token created successfully")
                        .data(result)
                        .build());
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<?> Logout(@CookieValue(name = "refresh_token") String refreshToken)
            throws Exception {
        AuthResponseDTO result = this.authService.Logout(refreshToken);

        return ResponseEntity.ok()
                .body(SuccessReponseDTO.builder()
                        .statusCode(200)
                        .message("Logged out successfully")
                        .data(result)
                        .build());
    }
}
