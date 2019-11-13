package com.svgptechnologies.ltg.Json.DriverJson.UploadDriverImage;

public class DriverImageUploadData {

    private String status;
    private String driver_id;

    private String driver_image;

    public DriverImageUploadData(String status, String driver_id, String driver_image) {
        this.status = status;
        this.driver_id = driver_id;
        this.driver_image = driver_image;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_image() {
        return driver_image;
    }

    public void setDriver_image(String driver_image) {
        this.driver_image = driver_image;
    }
}
