package com.svgptechnologies.ltg.Json.DriverJson.DriverLogin;


public class DriverLoginResponse {

    private String status;
    private DriverLoginData data;

    public DriverLoginResponse(String status, DriverLoginData data) {
        this.status = status;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DriverLoginData getData() {
        return data;
    }

    public void setData(DriverLoginData data) {
        this.data = data;
    }
}