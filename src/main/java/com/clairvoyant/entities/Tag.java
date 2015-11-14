package com.clairvoyant.entities;

import java.io.Serializable;

/**
 * Created by Clairvoyant on 27-10-2015.
 */
public class Tag implements Serializable{
    String id;
    String dine_in;
    String candle_light;
    String lake_view;
    String outdoor_seating;
    String bar;
    String smoking_area;
    String luxury_dining;
    String home_delivery;
    String non_veg;

    public class TagList{
        Tag[] tag;

        public Tag[] getTag() {
            return tag;
        }

        public void setTag(Tag[] tag) {
            this.tag = tag;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDine_in() {
        return dine_in;
    }

    public void setDine_in(String dine_in) {
        this.dine_in = dine_in;
    }

    public String getCandle_light() {
        return candle_light;
    }

    public void setCandle_light(String candle_light) {
        this.candle_light = candle_light;
    }

    public String getLake_view() {
        return lake_view;
    }

    public void setLake_view(String lake_view) {
        this.lake_view = lake_view;
    }

    public String getOutdoor_seating() {
        return outdoor_seating;
    }

    public void setOutdoor_seating(String outdoor_seating) {
        this.outdoor_seating = outdoor_seating;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getSmoking_area() {
        return smoking_area;
    }

    public void setSmoking_area(String smoking_area) {
        this.smoking_area = smoking_area;
    }

    public String getLuxury_dining() {
        return luxury_dining;
    }

    public void setLuxury_dining(String luxury_dining) {
        this.luxury_dining = luxury_dining;
    }

    public String getHome_delivery() {
        return home_delivery;
    }

    public void setHome_delivery(String home_delivery) {
        this.home_delivery = home_delivery;
    }

    public String getNon_veg() {
        return non_veg;
    }

    public void setNon_veg(String non_veg) {
        this.non_veg = non_veg;
    }
}
