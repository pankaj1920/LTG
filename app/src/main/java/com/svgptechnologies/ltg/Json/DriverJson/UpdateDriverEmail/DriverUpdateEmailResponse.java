package com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverEmail;

public class DriverUpdateEmailResponse {

    DriverUpdateEmailData data;

    public DriverUpdateEmailResponse(DriverUpdateEmailData data) {
        this.data = data;
    }

    public DriverUpdateEmailData getData() {
        return data;
    }

    public void setData(DriverUpdateEmailData data) {
        this.data = data;
    }
}


