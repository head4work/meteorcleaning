package com.example.meteorCleaning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prices")
public class OrderPrices {

    @Id
    private Boolean prices_id;

    @Column(name = "studio")
    private Integer studio;

    @Column(name = "apartments")
    private Integer apartments;

    @Column(name = "house")
    private Integer house;

    @Column(name = "housesqr")
    private double houseFt; //per sq ft

    @Column(name = "office")
    private double office; //per sq ft

    @Column(name = "bedroom")
    private Integer bedroom;

    @Column(name = "bathroom")
    private Integer bathroom; // half bathroom is 1/2

    @Column(name = "green")
    private Integer greenClean;

    @Column(name = "deep")
    private double deepClean; // coaf * multiply base + rooms

    @Column(name = "microwave")
    private Integer microwaveClean;

    @Column(name = "refrigerator")
    private Integer refrigeratorClean;

    @Column(name = "oven")
    private Integer ovenClean;

    @Column(name = "windows")
    private Integer windows;

    @Column(name = "cabinet")
    private Integer cabinet;

    @Column(name = "dishes")
    private Integer dishesWash;

    @Column(name = "weekend")
    private Integer weekend;

    @Column(name = "coupon")
    private String coupon;

    @Column(name = "discount")
    private double discount;

//
//    studio: 140,
//    apartments: 170,
//    house: 220,
//    houseFt: 0.5, //per sq ft
//    office: 0.3, //per sq ft
//    bedroom: 20,
//    bathroom: 24, // half bathroom is 1/2
//    greenClean: 40,
//    deepClean: 1.3, // coaf * multiply base + rooms
//    microwaveClean: 20,
//    refrigeratorClean: 30,
//    ovenClean: 30,
//    window: 8,
//    cabinet: 10,
//    dishesWash: 10,
//    weekend: 50


    public OrderPrices(Boolean prices_id, Integer studio, Integer apartments, Integer house, double houseFt, double office, Integer bedroom, Integer bathroom, Integer greenClean,
                       double deepClean, Integer microwaveClean, Integer refrigeratorClean, Integer ovenClean, Integer windows, Integer cabinet, Integer dishesWash,
                       Integer weekend) {
        this.prices_id = prices_id;
        this.studio = studio;
        this.apartments = apartments;
        this.house = house;
        this.houseFt = houseFt;
        this.office = office;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.greenClean = greenClean;
        this.deepClean = deepClean;
        this.microwaveClean = microwaveClean;
        this.refrigeratorClean = refrigeratorClean;
        this.ovenClean = ovenClean;
        this.windows = windows;
        this.cabinet = cabinet;
        this.dishesWash = dishesWash;
        this.weekend = weekend;
    }

    public OrderPrices(Boolean prices_id, Integer studio, Integer apartments, Integer house, double houseFt, double office, Integer bedroom,
                       Integer bathroom, Integer greenClean, double deepClean, Integer microwaveClean,
                       Integer refrigeratorClean, Integer ovenClean, Integer windows, Integer cabinet, Integer dishesWash,
                       Integer weekend, String coupon, double discount) {
        this.prices_id = prices_id;
        this.studio = studio;
        this.apartments = apartments;
        this.house = house;
        this.houseFt = houseFt;
        this.office = office;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.greenClean = greenClean;
        this.deepClean = deepClean;
        this.microwaveClean = microwaveClean;
        this.refrigeratorClean = refrigeratorClean;
        this.ovenClean = ovenClean;
        this.windows = windows;
        this.cabinet = cabinet;
        this.dishesWash = dishesWash;
        this.weekend = weekend;
        this.coupon = coupon;
        this.discount = discount;
    }

    public OrderPrices() {
    }

    public Integer getStudio() {
        return studio;
    }

    public void setStudio(Integer studio) {
        this.studio = studio;
    }

    public Integer getApartments() {
        return apartments;
    }

    public void setApartments(Integer apartments) {
        this.apartments = apartments;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public double getHouseFt() {
        return houseFt;
    }

    public void setHouseFt(double houseFt) {
        this.houseFt = houseFt;
    }

    public double getOffice() {
        return office;
    }

    public void setOffice(double office) {
        this.office = office;
    }

    public Integer getBedroom() {
        return bedroom;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setBedroom(Integer bedroom) {
        this.bedroom = bedroom;
    }

    public Integer getBathroom() {
        return bathroom;
    }

    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    public Integer getGreenClean() {
        return greenClean;
    }

    public void setGreenClean(Integer greenClean) {
        this.greenClean = greenClean;
    }

    public double getDeepClean() {
        return deepClean;
    }

    public void setDeepClean(double deepClean) {
        this.deepClean = deepClean;
    }

    public Integer getMicrowaveClean() {
        return microwaveClean;
    }

    public void setMicrowaveClean(Integer microwaveClean) {
        this.microwaveClean = microwaveClean;
    }

    public Integer getRefrigeratorClean() {
        return refrigeratorClean;
    }

    public void setRefrigeratorClean(Integer refrigeratorClean) {
        this.refrigeratorClean = refrigeratorClean;
    }

    public Integer getOvenClean() {
        return ovenClean;
    }

    public void setOvenClean(Integer ovenClean) {
        this.ovenClean = ovenClean;
    }

    public Integer getWindows() {
        return windows;
    }

    public void setWindows(Integer window) {
        this.windows = window;
    }

    public Integer getCabinet() {
        return cabinet;
    }

    public void setCabinet(Integer cabinet) {
        this.cabinet = cabinet;
    }

    public Integer getDishesWash() {
        return dishesWash;
    }

    public void setDishesWash(Integer dishesWash) {
        this.dishesWash = dishesWash;
    }

    public Integer getWeekend() {
        return weekend;
    }

    public void setWeekend(Integer weekend) {
        this.weekend = weekend;
    }


    public boolean isPrices_id() {
        return prices_id;
    }

    public void setPrices_id(boolean prices_id) {
        this.prices_id = prices_id;
    }
}
