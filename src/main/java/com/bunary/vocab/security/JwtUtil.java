package com.bunary.vocab.security;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtUtil {

    @Value("${jwt.access-token-validity}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenExpiration;

    @Value("${jwt.secret}")
    private String secretKeyBase64;

    private SecretKey secretKey;
    private JwtDecoder jwtDecoder;

    private static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyBase64);
        this.secretKey = new SecretKeySpec(keyBytes, getMacAlgorithm().getName());

        this.jwtDecoder = NimbusJwtDecoder
                .withSecretKey(getSecretKey())
                .macAlgorithm(getMacAlgorithm())
                .build();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public MacAlgorithm getMacAlgorithm() {
        return JWT_ALGORITHM;
    }
}
