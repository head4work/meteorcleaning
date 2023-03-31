package com.example.meteorCleaning.repository.datajpa;

import com.example.meteorCleaning.model.ForgottenPasswordToken;
import com.example.meteorCleaning.repository.TokenRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaTokenRepository {
    private final TokenRepository repository;

    public DataJpaTokenRepository(TokenRepository repository) {
        this.repository = repository;
    }

    public ForgottenPasswordToken create(ForgottenPasswordToken token) {
        return repository.save(token);
    }

    public ForgottenPasswordToken get(String token) {
        return repository.findByToken(token);
    }
}
