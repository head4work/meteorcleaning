package com.example.meteorCleaning.repository;

import com.example.meteorCleaning.model.ForgottenPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepository extends JpaRepository<ForgottenPasswordToken, Integer> {

 ForgottenPasswordToken findByToken(String token);

 @Modifying
 @Transactional
 @Query("DELETE FROM ForgottenPasswordToken t WHERE t.token=:token ")
 int deleteByToken(@Param("token") String token);

}
