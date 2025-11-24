package com.bunary.vocab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.SuccessReponseDTO;
import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.VerifyCodeReponseDTO;
import com.bunary.vocab.dto.request.UserRequestDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.service.VerifyCode.IVerifyCodeService;
import com.bunary.vocab.service.authentication.IAuthService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/api/v1")
@RestController
public class AuthController {
        @Autowired
        private IAuthService authService;

        @Autowired
        private IVerifyCodeService verifyCodeService;

        @Value("${spring.security.oauth2.client.registration.google.client-id}")
        private String clientId;

        @Value("${spring.security.oauth2.client.registration.google.client-secret}")
        private String clientSecret;

        @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
        private String redirectUri;

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
                                .header("Set-Cookie", result.getResponseCookie().toString())
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
                                .header("Set-Cookie", result.getResponseCookie().toString())
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Logged in successfully")
                                                .data(result)
                                                .build());
        }

        @GetMapping("/auth/oauth2/google")
        public String redirectToGoogle() {
                String url = "https://accounts.google.com/o/oauth2/v2/auth"
                                + "?client_id=" + clientId
                                + "&redirect_uri=" + redirectUri
                                + "&response_type=code"
                                + "&scope=email%20profile"
                                + "&access_type=offline"
                                + "&prompt=consent";

                return url;
        }

        @GetMapping("/auth/oauth2/google/callback")
        public void authenticateWithGoogle(@RequestParam("code") String code, HttpServletResponse response)
                        throws IOException {
                try {
                        AuthResponseDTO result = this.authService.authenticateWithGoogle(code);

                        response.addHeader("Set-Cookie", result.getResponseCookie().toString());
                        response.sendRedirect("/oauth2/success.html?accessToken=" + result.getAccessToken());
                } catch (ApiException e) {
                        response.sendRedirect("/oauth2/error.html?code=" + e.getErrorCode());
                }

        }

        @GetMapping("/auth/refresh-Token")
        public ResponseEntity<?> RefreshAccessToken(@CookieValue(name = "refresh_token") String refreshToken)
                        throws Exception {
                AuthResponseDTO result = this.authService.RefreshAccessToken(refreshToken);

                return ResponseEntity.ok()
                                .header("Set-Cookie", result.getResponseCookie().toString())
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
                                .header("Set-Cookie", result.getResponseCookie().toString())
                                .body(SuccessReponseDTO.builder()
                                                .statusCode(200)
                                                .message("Logged out successfully")
                                                .data(result)
                                                .build());
        }
}
