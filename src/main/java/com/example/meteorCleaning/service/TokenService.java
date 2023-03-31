package com.example.meteorCleaning.service;

import com.example.meteorCleaning.model.ForgottenPasswordToken;
import com.example.meteorCleaning.model.User;
import com.example.meteorCleaning.repository.datajpa.DataJpaTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    DataJpaTokenRepository repository;

    public boolean validateTokenExpired(ForgottenPasswordToken token) {
        return token.getExpiresAt().isAfter(LocalDateTime.now());
    }

    public ForgottenPasswordToken get(String token) {
        return repository.get(token);
    }

    public ForgottenPasswordToken create(User user) {
        String token = UUID.randomUUID().toString();
        ForgottenPasswordToken confirmationToken = new ForgottenPasswordToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(24),
                user
        );
        return repository.create(confirmationToken);
    }
}
