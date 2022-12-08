package com.example.security.service;

import com.example.security.dto.TokenRefreshException;
import com.example.security.model.RefreshToken;
import com.example.security.repo.RefreshTokenRepo;
import com.example.security.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;
    private final UserRepo userRepo;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo, UserRepo userRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.userRepo = userRepo;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }


    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepo.findByUsername(username));
        refreshToken.setExpiryDate(Instant.now().plusMillis(4000));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(String username) {
        return refreshTokenRepo.deleteByUser(userRepo.findByUsername(username));
    }
}
