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
    private String greenClean;
    private String deepClean;
    private String steamClean;
    private String microwaveClean;
    private String date;
    private String time;
    private String estimatedPrice;
    private String estimatedTime;

    public EstimateData(String name, String lastName, String address, String email, String phone, String housingType,
                        String squareFt, String bedrooms, String bathrooms, String greenClean, String deepClean, String steamClean,
                        String microwaveClean, String date, String time, String estimatedPrice, String estimatedTime) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.housingType = housingType;
        this.squareFt = squareFt;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.greenClean = greenClean;
        this.deepClean = deepClean;
        this.steamClean = steamClean;
        this.microwaveClean = microwaveClean;
        this.date = date;
        this.time = time;
        this.estimatedPrice = estimatedPrice;
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {

  String kek =
  "<h1>ESTIMATE ORDER DATA</h1>" +
  "<ul>" +
    "<h2>CLIENT DATA</h2>" +
    "<li>Name: "+ name +"</li>" +
    "<li>lastName: "+ lastName +"</li>" +
    "<li>Address: " + address + "</li>" +
    "<li>Email: "+ email +"</li>" +
    "<li>Phone: "+ phone +"</li>" +
    "<h2>HOUSE DATA</h2>" +
    "<li>Housing type: "+ housingType +"</li>" +
    "<li>Square ft: "+ squareFt +"</li>" +
    "<li>Bedrooms: "+ bedrooms + "</li>" +
    "<li>Bathrooms: "+ bathrooms +"</li>" +
    "<li>Green clean: "+ greenClean +"</li>" +
    "<li>Deep clean: "+ deepClean +"</li>" +
    "<li>Steam clean: "+ steamClean +"</li>" +
    "<li>Microwave clean: "+ microwaveClean +"</li>" +
    "<h2>DATE AND TIME</h2>" +
    "<li>Date: "+ date +"</li>" +
    "<li>Time: "+ time +"</li>" +
    "<h2>ESTIMATED TIME AND PRICE</h2>" +
    "<li>Estimated price: "+ estimatedPrice +"</li>" +
    "<li>Estimated time: "+ estimatedTime +"</li>" +
  "</ul>" ;


        return kek;
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

    public String getGreenClean() {
        return greenClean;
    }

    public void setGreenClean(String greenClean) {
        this.greenClean = greenClean;
    }

    public String getDeepClean() {
        return deepClean;
    }

    public void setDeepClean(String deepClean) {
        this.deepClean = deepClean;
    }

    public String getSteamClean() {
        return steamClean;
    }

    public void setSteamClean(String steamClean) {
        this.steamClean = steamClean;
    }

    public String getMicrowaveClean() {
        return microwaveClean;
    }

    public void setMicrowaveClean(String microwaveClean) {
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
}
