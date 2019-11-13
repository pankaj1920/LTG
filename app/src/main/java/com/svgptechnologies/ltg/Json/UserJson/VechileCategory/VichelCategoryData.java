package com.svgptechnologies.ltg.Json.UserJson.VechileCategory;

public class VichelCategoryData {

    private String id;
    private String vehicle_type;
    private String vehicle_icon;
    private String booknow;
    private String booklater;

    public VichelCategoryData(String id, String vehicle_type, String vehicle_icon, String booknow, String booklater) {
        this.id = id;
        this.vehicle_type = vehicle_type;
        this.vehicle_icon = vehicle_icon;
        this.booknow = booknow;
        this.booklater = booklater;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_icon() {
        return vehicle_icon;
    }

    public void setVehicle_icon(String vehicle_icon) {
        this.vehicle_icon = vehicle_icon;
    }

    public String getBooknow() {
        return booknow;
    }

    public void setBooknow(String booknow) {
        this.booknow = booknow;
    }

    public String getBooklater() {
        return booklater;
    }

    public void setBooklater(String booklater) {
        this.booklater = booklater;
    }
}
