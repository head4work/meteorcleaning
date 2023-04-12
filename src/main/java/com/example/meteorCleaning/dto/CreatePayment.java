package com.example.meteorCleaning.dto;

public class CreatePayment {

    private String email;

    private int amount;

    public CreatePayment() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
