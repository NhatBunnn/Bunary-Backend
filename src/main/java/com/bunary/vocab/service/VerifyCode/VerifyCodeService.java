package com.bunary.vocab.service.VerifyCode;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.VerifyCodeReponseDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.model.VerifyCode;
import com.bunary.vocab.repository.VerifyCodeRepository;
import com.bunary.vocab.security.JwtTokenProvider;
import com.bunary.vocab.security.JwtUtil;
import com.bunary.vocab.service.email.IEmailService;
import com.bunary.vocab.service.user.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VerifyCodeService implements IVerifyCodeService {
    private VerifyCodeRepository verifyCodeRepository;
    private SecureRandom secureRandom;
    private IUserService userService;
    private final IEmailService emailService;
    private final JwtUtil jwtUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    public String generate6Digits() {
        int n = secureRandom.nextInt(1_000_000);
        return String.format("%06d", n);
    }

    @Override
    public void delete(VerifyCode verifyCode) {
        this.verifyCodeRepository.delete(verifyCode);
    }

    @Override
    public boolean sendCode(User user) {
        if (user == null)
            return false;

        VerifyCode verifyCode = this.verifyCodeRepository.findByUserId(user.getId());

        if (verifyCode == null) {
            verifyCode = new VerifyCode();
            verifyCode.setUserId(user.getId());
        } else {
            if (verifyCode.getRetryAvailableAt().isAfter(Instant.now()))
                throw new ApiException(ErrorCode.EMAIL_CODE_ATTEMPTS_EXCEEDED);
        }

        verifyCode.setUsed(false);
        verifyCode.setExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES));
        verifyCode.setCode(generate6Digits());

        this.verifyCodeRepository.save(verifyCode);

        this.emailService.sendVerificationCode(user.getEmail(), verifyCode.getCode());

        return true;
    }

    @Override
    public AuthResponseDTO verifyCode(VerifyCodeReponseDTO verifyCode) {
        User user = this.userService.findByEmail(verifyCode.getEmail());
        VerifyCode currentVerifyCode = this.verifyCodeRepository.findByUserId(user.getId());

        if (user.isEmailVerified())
            throw new ApiException(ErrorCode.USER_NOT_VERIFIED);

        if (currentVerifyCode.getRetryAvailableAt().isAfter(Instant.now()))
            throw new ApiException(ErrorCode.EMAIL_CODE_ATTEMPTS_EXCEEDED);

        if (currentVerifyCode.isUsed())
            throw new ApiException(ErrorCode.EMAIL_CODE_USED);

        if (verifyCode.getCode().isEmpty() || !verifyCode.getCode().equals(currentVerifyCode.getCode())) {
            currentVerifyCode.setUsed(true);

            int verifyAttempts = currentVerifyCode.getFailedVerifyAttempts();
            verifyAttempts++;

            currentVerifyCode.setFailedVerifyAttempts(verifyAttempts);

            if (verifyAttempts >= 3) {
                currentVerifyCode.setFailedVerifyAttempts(0);
                currentVerifyCode.setRetryAvailableAt(Instant.now().plus(30, ChronoUnit.MINUTES));
            }

            this.verifyCodeRepository.save(currentVerifyCode);

            throw new ApiException(ErrorCode.EMAIL_CODE_INVALID);
        }

        if (currentVerifyCode.getExpiresAt().isBefore(Instant.now()))
            throw new ApiException(ErrorCode.EMAIL_CODE_EXPIRED);

        this.verifyCodeRepository.save(currentVerifyCode);

        user.setEmailVerified(true);
        this.userService.save(user);

        delete(currentVerifyCode);

        String accessToken = this.jwtTokenProvider.generateAccessToken(user);
        String refreshToken = this.jwtTokenProvider.generateRefreshToken(user);

        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtUtil.getRefreshTokenExpiration())
                .sameSite("None")
                .build();

        AuthResponseDTO authDTO = new AuthResponseDTO();
        authDTO = userMapper.convertToAuthResponseDTO(user, accessToken, refreshToken, responseCookie);
        return authDTO;
    }

}
