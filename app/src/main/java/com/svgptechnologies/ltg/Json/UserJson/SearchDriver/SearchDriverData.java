package com.svgptechnologies.ltg.Json.UserJson.SearchDriver;

public class SearchDriverData {

    private String status;
    private String id;
    private String name;
    private String mobile;
    private String email;
    private String driver_image;
    private String driver_status;
    private String ratings;
    private String vehical_type;


    public SearchDriverData(String status, String id, String name, String mobile, String email, String driver_image, String driver_status, String ratings, String vehical_type) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.driver_image = driver_image;
        this.driver_status = driver_status;
        this.ratings = ratings;
        this.vehical_type = vehical_type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriver_image() {
        return driver_image;
    }

    public void setDriver_image(String driver_image) {
        this.driver_image = driver_image;
    }

    public String getDriver_status() {
        return driver_status;
    }

    public void setDriver_status(String driver_status) {
        this.driver_status = driver_status;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getVehical_type() {
        return vehical_type;
    }

    public void setVehical_type(String vehical_type) {
        this.vehical_type = vehical_type;
    }
}

