package com.bunary.vocab.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunary.vocab.model.RefreshToken;
import com.bunary.vocab.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken save(RefreshToken refreshToken);

    RefreshToken findByRefreshTokenAndUserAndIsRevokedFalse(String refreshToken, User user);

    void delete(RefreshToken refreshToken);
}
