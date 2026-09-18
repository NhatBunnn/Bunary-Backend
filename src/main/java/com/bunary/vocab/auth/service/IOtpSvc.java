package com.bunary.vocab.auth.service;

import com.bunary.vocab.dto.reponse.AuthResponseDTO;
import com.bunary.vocab.dto.reponse.OtpResponseDTO;

public interface IOtpSvc {

    String generateOtp();

    void saveOtp(String email, String otp);

    String getOtp(String email);

    void deleteOtp(String email);

    void sendOtp(String email);

    void createResendCooldown(String email);

    boolean isResendAvailable(String email);

    int getAttempts(String email);

    int increaseAttempts(String email);

    void lockOtp(String email);

    boolean isOtpLocked(String email);

    AuthResponseDTO verifyOtp(OtpResponseDTO otpResponse);

}