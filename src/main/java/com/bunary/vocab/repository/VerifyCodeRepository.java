package com.bunary.vocab.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.User;
import com.bunary.vocab.model.VerifyCode;

public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Long> {
    Optional<VerifyCode> findTopByUserOrderByCreatedAtDesc(User user);

    long deleteByUserAndExpiresAtBefore(User user, Instant time);

    VerifyCode findByUser(User user);

    void delete(VerifyCode verifyCode);
}
