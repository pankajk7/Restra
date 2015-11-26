package com.clairvoyant.entities;


import java.io.Serializable;

public class Area implements Serializable {
    String id;
    String area;

    public class AreaList{
        Area[] area;

        public Area[] getArea() {
            return area;
        }

        public void setArea(Area[] area) {
            this.area = area;
        }
    }

    @Override
    public String toString() {
        return area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
