package com.example.meteorCleaning.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class ForgottenPasswordToken extends AbstractBaseEntity {

    @NotNull
    @Column(nullable = false)
    private String token;

    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "expire", nullable = false)
    private LocalDateTime expiresAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ForgottenPasswordToken() {
    }

    public ForgottenPasswordToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String createEmail(String baseUrl) {
        String url = baseUrl + token;
        String htmlMailString =
                "<h1>Password Restore Request</h1>" +
                        "<p>Dear " + user.name + "</p>" +
                        "<p>You requested a password restore on Meteorcleaning website, the following link will provide a form to change the password,\n" +
                        "the link would be valid for 24 hours</p>" +
                        " <a  href='" + url + "'>Click here to restore password </a>" +
                        "<p>If you have any questions please contact us by phone or email</p>";
        return htmlMailString;
    }
}
