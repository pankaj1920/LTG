package com.svgptechnologies.ltg.Json.DriverJson.DriverUpdatePassword;

public class DriverUpdatePasswordResponse {


    DriverUpdatePasswordData data;

    public DriverUpdatePasswordResponse(DriverUpdatePasswordData data) {
        this.data = data;
    }

    public DriverUpdatePasswordData getData() {
        return data;
    }

    public void setData(DriverUpdatePasswordData data) {
        this.data = data;
    }
}


