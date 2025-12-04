package com.bunary.vocab.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.bunary.vocab.model.RefreshToken;
import com.bunary.vocab.model.User;
import com.bunary.vocab.service.refreshToken.IRefreshTokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtTokenProvider {
        private JwtEncoder jwtEncoder;
        private JwtUtil jwtUtil;
        private IRefreshTokenService refreshTokenService;

        public String generateAccessToken(User user) {
                Instant now = Instant.now();
                Instant expiry = now.plus(this.jwtUtil.getAccessTokenExpiration(), ChronoUnit.SECONDS);

                Set<String> roles = new HashSet<>();
                Set<String> permissions = new HashSet<>();

                user.getRoles().forEach(role -> {
                        roles.add(role.getName());
                        role.getPermissions().forEach(p -> {
                                permissions.add(p.getName());
                        });
                });

                JwsHeader header = JwsHeader.with(this.jwtUtil.getMacAlgorithm()).build();

                JwtClaimsSet payload = JwtClaimsSet.builder()
                                .issuedAt(now)
                                .expiresAt(expiry)
                                .subject(user.getId() + "")
                                .claim("user", Map.of(
                                                "email", user.getEmail(),
                                                "firstName", user.getFirstName(),
                                                "lastName", user.getLastName()))
                                .claim("roles", roles)
                                .claim("scope", String.join(" ", permissions))
                                .claim("type", "access")
                                .build();

                String token = this.jwtEncoder.encode(JwtEncoderParameters.from(header, payload)).getTokenValue();

                return token;
        }

        public String generateRefreshToken(User user) {
                Instant now = Instant.now();
                Instant expiry = now.plus(jwtUtil.getRefreshTokenExpiration(), ChronoUnit.SECONDS);

                JwsHeader header = JwsHeader.with(this.jwtUtil.getMacAlgorithm()).build();

                JwtClaimsSet payload = JwtClaimsSet.builder()
                                .issuedAt(now)
                                .expiresAt(expiry)
                                .subject(user.getId() + "")
                                .claim("type", "refresh")
                                .build();

                String token = this.jwtEncoder.encode(JwtEncoderParameters.from(header, payload)).getTokenValue();

                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setUser(user);
                refreshToken.setRefreshToken(token);
                refreshToken.setCreatedAt(now);
                refreshToken.setExpiryDate(expiry);

                this.refreshTokenService.save(refreshToken);

                return token;
        }

}
