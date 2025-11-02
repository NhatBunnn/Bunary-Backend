package com.bunary.vocab.repository;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.VerifyCode;

public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Long> {
    long deleteByUserIdAndExpiresAtBefore(UUID userId, Instant time);

    VerifyCode findByUserId(UUID userId);

    void delete(VerifyCode verifyCode);
}
