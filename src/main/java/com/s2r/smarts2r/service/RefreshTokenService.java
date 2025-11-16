package com.s2r.smarts2r.service;

import com.s2r.smarts2r.model.RefreshToken;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${s2r.refresh.expiration:2592000000}") // default 30 days in ms
    private long refreshExpirationMillis;

    public RefreshToken createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        Instant expires = Instant.now().plusMillis(refreshExpirationMillis);
        RefreshToken rt = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiresAt(expires)
                .build();
        return refreshTokenRepository.save(rt);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void revoke(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
