package com.svgptechnologies.ltg.Json.DriverJson.GetDriverDetails;

public class GetDriverDetailData {

    String status;
    public String id;
    public String name;
    public String mobile;
    public String driver_image;


    public GetDriverDetailData(String status, String id, String name, String mobile, String driver_image) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.driver_image = driver_image;
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

    public String getDriver_image() {
        return driver_image;
    }

    public void setDriver_image(String driver_image) {
        this.driver_image = driver_image;
    }
}
