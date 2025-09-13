package com.bunary.vocab.service.refreshToken;

import java.util.UUID;

import com.bunary.vocab.model.RefreshToken;
import com.bunary.vocab.model.User;

public interface IRefreshTokenService {
    RefreshToken save(RefreshToken refreshToken);

    RefreshToken findByRefreshTokenAndUser(String refreshToken, User user);

    void delete(RefreshToken refreshToken);

    void update(RefreshToken refreshToken);
}
