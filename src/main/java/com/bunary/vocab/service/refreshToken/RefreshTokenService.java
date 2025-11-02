package com.bunary.vocab.service.refreshToken;

import org.springframework.stereotype.Service;

import com.bunary.vocab.model.RefreshToken;
import com.bunary.vocab.model.User;
import com.bunary.vocab.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RefreshTokenService implements IRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return this.refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findByRefreshTokenAndUser(String refreshToken, User user) {
        return this.refreshTokenRepository.findByRefreshTokenAndUserAndIsRevokedFalse(refreshToken, user);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        this.refreshTokenRepository.delete(refreshToken);
    }

    public void update(RefreshToken refreshToken) {
        this.refreshTokenRepository.save(refreshToken);
    }

}
