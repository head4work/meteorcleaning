package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.ForgottenPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<ForgottenPasswordToken, Integer> {
 ForgottenPasswordToken findByToken(String token);

}
