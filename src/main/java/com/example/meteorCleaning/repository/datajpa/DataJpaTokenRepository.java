package com.example.meteorCleaning.repository.datajpa;

import com.example.meteorCleaning.model.ForgottenPasswordToken;
import com.example.meteorCleaning.repository.TokenRepository;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public class DataJpaTokenRepository {
    private final TokenRepository repository;

    public DataJpaTokenRepository(TokenRepository repository) {
        this.repository = repository;
    }

    public ForgottenPasswordToken create(ForgottenPasswordToken token) {
        return repository.save(token);
    }

    public ForgottenPasswordToken get(String token) throws Throwable {
        return repository.findByToken(token).orElseThrow((Supplier<Throwable>) () -> null);
    }
}
