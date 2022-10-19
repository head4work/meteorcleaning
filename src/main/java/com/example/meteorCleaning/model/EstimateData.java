package com.example.meteorCleaning.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EstimateData {

    @NotBlank(message = "Please enter your name")
    private String name;

    private String lastName;

    @NotBlank(message = "Please fill the address")
    private String address;
    @Email
    @NotBlank(message = "Enter proper email address please")
    private String email;

    @NotBlank(message = "Enter proper phone number")
    private String phone;

    private String housingType;
    private String squareFt;
    private String bedrooms;
    private String bathrooms;
    private String halfBathrooms;

    private boolean greenClean;
    private boolean deepClean;
    private boolean microwaveClean;
    private boolean refrigeratorClean;
    private boolean ovenClean;
    private boolean dishesClean;

    private String date;
    private String time;
    private String estimatedPrice;
    private String estimatedTime;


    public EstimateData(String name, String lastName, String address, String email, String phone, String housingType,
                        String squareFt, String bedrooms, String bathrooms, String halfBathrooms, boolean greenClean, boolean deepClean,
                        boolean microwaveClean, boolean refrigeratorClean, boolean ovenClean, boolean dishesClean, String date, String time, String estimatedPrice, String estimatedTime) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.housingType = housingType;
        this.squareFt = squareFt;
        this.bedrooms = bedrooms;
        this.halfBathrooms = halfBathrooms;
        this.refrigeratorClean = refrigeratorClean;
        this.ovenClean = ovenClean;
        this.dishesClean = dishesClean;
        this.bathrooms = bathrooms;
        this.greenClean = greenClean;
        this.deepClean = deepClean;
        this.microwaveClean = microwaveClean;
        this.date = date;
        this.time = time;
        this.estimatedPrice = estimatedPrice;
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {

        String yes = "&#9989;";
        String no = "&#10006;";

        String green = greenClean ? yes : no;
        String deep = deepClean ? yes : no;
        String micro = microwaveClean ? yes : no;
        String ref = refrigeratorClean ? yes : no;
        String oven = ovenClean ? yes : no;
        String dish = dishesClean ? yes : no;


        String htmlMailString =
                "<h1>ESTIMATE ORDER DATA</h1>" +
                        "<ul>" +
                        "<h2>CLIENT DATA</h2>" +
                        "<li>Name: " + name + "</li>" +
                        "<li>lastName: " + lastName + "</li>" +
                        "<li>Address: " + address + "</li>" +
                        "<li>Email: " + email + "</li>" +
                        "<li>Phone: " + phone + "</li>" +
                        "<h2>HOUSE DATA</h2>" +
                        "<li>Housing type: " + housingType + "</li>" +
                        "<li>Square ft: " + squareFt + "</li>" +
                        "<li>Bedrooms: " + bedrooms + "</li>" +
                        "<li>Bathrooms: " + bathrooms + "</li>" +
                        "<li>1/2 bathrooms: " + halfBathrooms + "</li>" +
                        "<li>Green clean: " + green + "</li>" +
                        "<li>Deep clean: " + deep + "</li>" +
                        "<li>Microwave clean: " + micro + "</li>" +
                        "<li>Refrigerator clean: " + ref + "</li>" +
                        "<li>Oven clean: " + oven + "</li>" +
                        "<li>Dishes wash: " + dish + "</li>" +
                        "<h2>DATE AND TIME</h2>" +
                        "<li>Date: " + date + "</li>" +
                        "<li>Time: " + time + "</li>" +
                        "<h2>ESTIMATED TIME AND PRICE</h2>" +
                        "<li>Estimated price: " + estimatedPrice + "</li>" +
                        "<li>Estimated time: " + estimatedTime + "</li>" +
                        "</ul>";


        return htmlMailString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHousingType() {
        return housingType;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public boolean getGreenClean() {
        return greenClean;
    }

    public void setGreenClean(boolean greenClean) {
        this.greenClean = greenClean;
    }

    public boolean getDeepClean() {
        return deepClean;
    }

    public void setDeepClean(boolean deepClean) {
        this.deepClean = deepClean;
    }

    public boolean getMicrowaveClean() {
        return microwaveClean;
    }

    public void setMicrowaveClean(boolean microwaveClean) {
        this.microwaveClean = microwaveClean;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getSquareFt() {
        return squareFt;
    }

    public void setSquareFt(String squareFt) {
        this.squareFt = squareFt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getHalfBathrooms() {
        return halfBathrooms;
    }

    public void setHalfBathrooms(String halfBathrooms) {
        this.halfBathrooms = halfBathrooms;
    }

    public boolean getRefrigeratorClean() {
        return refrigeratorClean;
    }

    public void setRefrigeratorClean(boolean refrigeratorClean) {
        this.refrigeratorClean = refrigeratorClean;
    }

    public boolean getOvenClean() {
        return ovenClean;
    }

    public void setOvenClean(boolean ovenClean) {
        this.ovenClean = ovenClean;
    }

    public boolean getDishesClean() {
        return dishesClean;
    }

    public void setDishesClean(boolean dishesClean) {
        this.dishesClean = dishesClean;
    }
}
