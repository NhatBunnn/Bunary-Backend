package com.bunary.vocab.service.refreshToken;

import com.bunary.vocab.model.RefreshToken;
import com.bunary.vocab.model.User;

public interface IRefreshTokenService {
    RefreshToken save(RefreshToken refreshToken);

    RefreshToken findByRefreshTokenAndUser(String refreshToken, User user);

    void delete(RefreshToken refreshToken);

    void update(RefreshToken refreshToken);
}
