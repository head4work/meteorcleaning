package com.example.meteorCleaning.dto;


import com.example.meteorCleaning.model.ForgottenPasswordToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

public class PasswordRecoveryTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    private String password;

    @NotNull
    private ForgottenPasswordToken token;

    public PasswordRecoveryTo() {
    }

    public PasswordRecoveryTo(ForgottenPasswordToken token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForgottenPasswordToken getToken() {
        return token;
    }

    public void setToken(ForgottenPasswordToken token) {
        this.token = token;
    }
}
