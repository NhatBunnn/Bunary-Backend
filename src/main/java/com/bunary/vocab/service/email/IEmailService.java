package com.bunary.vocab.service.email;

public interface IEmailService {
    void sendVerificationCode(String toEmail, String code);
}
