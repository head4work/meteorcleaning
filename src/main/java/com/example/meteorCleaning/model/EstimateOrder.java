package com.example.meteorCleaning.model;

import com.example.meteorCleaning.util.TimeUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class EstimateOrder extends AbstractNamedEntity {

    @Column(name = "create_date", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate = LocalDate.now();

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    @NotBlank(message = "{Size.Order.Address}")
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @Size(max = 100)
    @NotBlank(message = "{Size.Order.Email}")
    private String email;

    @NotBlank(message = "{Size.Order.Phone}")
    private String phone;

    @Column(name = "housing_type", nullable = false)
    @NotBlank
    private String housingType;

    @Column(name = "square_ft")
    private String squareFt;

    @Column(name = "bedrooms")
    private String bedrooms;

    @Column(name = "bathrooms")
    private String bathrooms;

    @Column(name = "half_bathrooms")
    private String halfBathrooms;

    @Column(name = "green_clean", columnDefinition = "bool default false")
    private boolean greenClean;

    @Column(name = "deep_clean", columnDefinition = "bool default false")
    private boolean deepClean;

    @Column(name = "microwave_clean", columnDefinition = "bool default false")
    private boolean microwaveClean;

    @Column(name = "refrigerator_clean", columnDefinition = "bool default false")
    private boolean refrigeratorClean;

    @Column(name = "oven_clean", columnDefinition = "bool default false")
    private boolean ovenClean;

    @Column(name = "windows")
    @Min(value = 0, message = "{Size.Order.Windows]")
    @Max(value = 15, message = "{Size.Order.Windows]")
    private Integer windowClean;

    @Column(name = "cabinets")
    @Min(value = 0, message = "{Size.Order.Cabinets]")
    @Max(value = 15, message = "{Size.Order.Cabinets]")
    private Integer cabinetClean;

    @Column(name = "dishes_clean", columnDefinition = "bool default false")
    private boolean dishesClean;

    @Column(name = "date_time")
    @NotNull(message = "{Size.Order.Date}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)

    private LocalDateTime dateTime;

    @Column(name = "estimated_price")
    private Integer estimatedPrice;

    @Column(name = "estimated_time")
    private String estimatedTime;


    public EstimateOrder(String name, String lastName, String address, String email, String phone, String housingType,
                         String squareFt, String bedrooms, String bathrooms, String halfBathrooms, boolean greenClean, boolean deepClean,
                         boolean microwaveClean, boolean refrigeratorClean, boolean ovenClean, Integer windowClean, Integer cabinetClean, boolean dishesClean, @NotBlank(message = "{Size.Order.Date}") LocalDateTime dateTime, Integer estimatedPrice, String estimatedTime) {
        this.windowClean = windowClean;
        this.cabinetClean = cabinetClean;
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
        this.dateTime = dateTime;
        this.estimatedPrice = estimatedPrice;
        this.estimatedTime = estimatedTime;
    }

    public EstimateOrder() {
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
        String window = windowClean > 0 ? String.valueOf(windowClean) : no;
        String cabinet = cabinetClean > 0 ? String.valueOf(cabinetClean) : no;
        String dish = dishesClean ? yes : no;
        String square = (squareFt == null) ? "none" : squareFt;
        String house;
        switch (Integer.parseInt(housingType)) {
            case 1:
                house = "Apartments";
                break;
            case 2:
                house = "House";
                break;
            case 3:
                house = "Office";
                break;
            case 0:
            default:
                house = "Studio";
        }

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
                        "<li>Housing type: " + house + "</li>" +
                        "<li>Square ft: " + square + "</li>" +
                        "<li>Bedrooms: " + bedrooms + "</li>" +
                        "<li>Bathrooms: " + bathrooms + "</li>" +
                        "<li>1/2 bathrooms: " + halfBathrooms + "</li>" +
                        "<li>Green clean: " + green + "</li>" +
                        "<li>Deep clean: " + deep + "</li>" +
                        "<li>Microwave clean: " + micro + "</li>" +
                        "<li>Refrigerator clean: " + ref + "</li>" +
                        "<li>Oven clean: " + oven + "</li>" +
                        "<li>Windows to clean: " + window + "</li>" +
                        "<li>Cabinets to clean: " + cabinet + "</li>" +
                        "<li>Dishes wash: " + dish + "</li>" +
                        "<h2>DATE AND TIME</h2>" +
                        "<li>Date: " + TimeUtil.humanDate(dateTime) + "</li>" +
                        "<li>Time: " + TimeUtil.humanTime(dateTime) + "</li>" +
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime date) {
        this.dateTime = date;
    }

    public Integer getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Integer estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getSquareFt() {
        return squareFt;
    }

    public void setSquareFt(String squareFt) {
        this.squareFt = squareFt;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Integer getWindowClean() {
        return windowClean;
    }

    public void setWindowClean(Integer windowClean) {
        this.windowClean = windowClean;
    }

    public Integer getCabinetClean() {
        return cabinetClean;
    }

    public void setCabinetClean(Integer cabinetClean) {
        this.cabinetClean = cabinetClean;
    }
}
