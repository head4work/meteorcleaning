package com.example.meteorCleaning.dto;

import com.example.meteorCleaning.HasId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public abstract class BaseTo implements HasId {
    protected Integer id;

    @Email
    @NotBlank
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\D-]{2,4}$", message = "format is invalid")
    @Size(max = 100, message = "length must be max 100 characters")
    protected String email;

    public BaseTo() {
    }

    public BaseTo(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
