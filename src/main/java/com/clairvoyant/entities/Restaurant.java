package com.clairvoyant.entities;

import java.io.Serializable;

public class Restaurant implements Serializable {
    String id;
    String name;
    String address;
    String area;
    String city;
    String state;
    String phone;
    String email;
    String website;
    String rating;
    String latitude;
    String longitude;
    String cuisines;
    String open_hour;
    String close_hour;
    String cost;
    String payment_methods;
    String primary_image;



    public class RestaurantList{
        public Restaurant[] getRestaurant() {
            return restaurant;
        }

        public void setRestaurant(Restaurant[] restaurant) {
            this.restaurant = restaurant;
        }

        Restaurant[] restaurant;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getOpen_hour() {
        return open_hour;
    }

    public void setOpen_hour(String open_hour) {
        this.open_hour = open_hour;
    }

    public String getClose_hour() {
        return close_hour;
    }

    public void setClose_hour(String close_hour) {
        this.close_hour = close_hour;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPayment_methods() {
        return payment_methods;
    }

    public void setPayment_methods(String payment_methods) {
        this.payment_methods = payment_methods;
    }

    public String getPrimary_image() {
        return primary_image;
    }

    public void setPrimary_image(String primary_image) {
        this.primary_image = primary_image;
    }
}
