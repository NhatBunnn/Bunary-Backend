package com.bunary.vocab.auth.service.impl;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction.Return;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.bunary.vocab.auth.service.IOtpSvc;
import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.OtpResponseDTO;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.mapper.UserMapper;
import com.bunary.vocab.model.User;
import com.bunary.vocab.security.JwtTokenProvider;
import com.bunary.vocab.security.JwtUtil;
import com.bunary.vocab.service.email.IEmailService;
import com.bunary.vocab.service.user.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OtpSvc implements IOtpSvc {

    private final IEmailService emailService;
    private final StringRedisTemplate redisTemplate;
    private final IUserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    public String generateOtp() {

        return String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 1000000));
    }

    @Override
    public void saveOtp(String email, String otp) {

        String key = "otp:" + email;

        redisTemplate.opsForValue().set(
                key,
                otp,
                5,
                TimeUnit.MINUTES);
    }

    @Override
    public String getOtp(String email) {

        String key = "otp:" + email;

        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteOtp(String email) {

        String key = "otp:" + email;

        redisTemplate.delete(key);
    }

    @Override
    public void sendOtp(String email) {

        if (isOtpLocked(email)) {
            throw new ApiException(ErrorCode.OTP_LOCKED);
        }

        if (!isResendAvailable(email)) {
            throw new ApiException(ErrorCode.OTP_RESEND_TOO_SOON);
        }

        String otp = generateOtp();

        saveOtp(email, otp);

        createResendCooldown(email);

        emailService.sendVerificationCode(email, otp);
    }

    @Override
    public void createResendCooldown(String email) {

        String key = "otp:resend:" + email;

        redisTemplate.opsForValue().set(
                key,
                "1", // đánh dấu e nó đang tồn tại thôi
                30,
                TimeUnit.SECONDS);
    }

    @Override
    public boolean isResendAvailable(String email) {

        String key = "otp:resend:" + email;

        Long ttl = redisTemplate.getExpire(
                key,
                TimeUnit.SECONDS);

        return ttl == null || ttl <= 0;
    }

    public int getAttempts(String email) {

        String key = "otp:attempt:" + email;

        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return 0;
        }

        return Integer.parseInt(value);
    }

    public int increaseAttempts(String email) {

        String key = "otp:attempt:" + email;

        Long attempts = redisTemplate.opsForValue().increment(key);

        if (attempts == 1) {
            redisTemplate.expire(
                    key,
                    30,
                    TimeUnit.MINUTES);
        }

        return attempts.intValue();
    }

    private void deleteAttempts(String email) {

        String key = "otp:attempt:" + email;

        redisTemplate.delete(key);
    }

    public void lockOtp(String email) {

        String key = "otp:lock:" + email;

        redisTemplate.opsForValue().set(
                key,
                "1",
                30,
                TimeUnit.MINUTES);
    }

    public boolean isOtpLocked(String email) {

        String key = "otp:lock:" + email;

        Boolean exists = redisTemplate.hasKey(key);

        return exists != null && exists;
    }

    public AuthResponseDTO verifyOtp(OtpResponseDTO otpResponse) {

        String email = otpResponse.getEmail();

        User user = this.userService.findByEmail(email);

        if (user == null)
            throw new ApiException(ErrorCode.USER_NOT_FOUND);

        if (user.isEmailVerified())
            throw new ApiException(ErrorCode.USER_ALREADY_VERIFIED);

        if (isOtpLocked(otpResponse.getEmail()))
            throw new ApiException(ErrorCode.OTP_LOCKED);

        String otp = getOtp(otpResponse.getEmail());

        if (otp == null)
            throw new ApiException(ErrorCode.OTP_EXPIRED);

        if (!otp.equals(otpResponse.getOtp())) {
            deleteOtp(email);

            int attempts = increaseAttempts(email);

            if (attempts >= 3) {
                lockOtp(email);
                throw new ApiException(ErrorCode.OTP_LOCKED);
            }

            throw new ApiException(ErrorCode.OTP_INVALID);
        }

        // Success
        user.setEmailVerified(true);

        this.userService.save(user);

        deleteOtp(email);
        deleteAttempts(email);

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