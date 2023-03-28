package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.ForgottenPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<ForgottenPasswordToken, Integer> {
 Optional<ForgottenPasswordToken> findByToken(String token);

}
