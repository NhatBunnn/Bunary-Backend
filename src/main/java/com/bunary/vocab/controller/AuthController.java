package com.bunary.vocab.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
import com.bunary.vocab.exception.CustomException.BadRequestException;
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
        if (!result) {
            return ResponseEntity.badRequest().body(GlobalErrorCode.ACCOUNT_CREATION_FAILED);
        } else {
            return ResponseEntity.ok()
                    .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("ok rồi"), null));
        }
    }

    @PostMapping("/auth/verify-email")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeReponseDTO verifyCode) {
        AuthResponseDTO result = this.verifyCodeService.verifyCode(verifyCode);
        if (result == null) {
            throw new BadRequestException(GlobalErrorCode.CODE_INVALID);
        } else {
            return ResponseEntity.ok()
                    .header("Set-Cookie", result.getResponseCookie().toString())
                    .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Xác thực thành công"), result));
        }

    }

    @PostMapping("/auth/send-code")
    public ResponseEntity<?> resendCode(@RequestBody UserRequestDTO userReq) {
        boolean result = this.authService.sendCode(userReq);
        if (!result) {
            throw new BadRequestException(GlobalErrorCode.SEND_CODE_FAILED);

        } else {
            return ResponseEntity.ok()
                    .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Gửi mã thành công"), null));
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> Login(@RequestBody UserRequestDTO user) throws Exception {
        AuthResponseDTO result = this.authService.Login(user);

        return ResponseEntity.ok()
                .header("Set-Cookie", result.getResponseCookie().toString())
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Đăng nhập thành công"), result));
    }

    @GetMapping("/auth/refresh-Token")
    public ResponseEntity<?> RefreshAccessToken(@CookieValue(name = "refresh_token") String refreshToken)
            throws Exception {
        AuthResponseDTO result = this.authService.RefreshAccessToken(refreshToken);

        return ResponseEntity.ok()
                .header("Set-Cookie", result.getResponseCookie().toString())
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Tạo mới Token thành công"), result));
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<?> Logout(@CookieValue(name = "refresh_token") String refreshToken)
            throws Exception {
        AuthResponseDTO result = this.authService.Logout(refreshToken);

        return ResponseEntity.ok()
                .header("Set-Cookie", result.getResponseCookie().toString())
                .body(new SuccessReponseDTO<>(LocalDateTime.now(), 202, List.of("Đăng xuất thành công"), result));
    }
}
