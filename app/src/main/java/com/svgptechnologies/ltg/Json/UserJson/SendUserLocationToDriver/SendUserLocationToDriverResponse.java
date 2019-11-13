package com.svgptechnologies.ltg.Json.UserJson.SendUserLocationToDriver;

public class SendUserLocationToDriverResponse {

    SendUserLocationToDriverData data;

    public SendUserLocationToDriverResponse(SendUserLocationToDriverData data) {
        this.data = data;
    }

    public SendUserLocationToDriverData getData() {
        return data;
    }

    public void setData(SendUserLocationToDriverData data) {
        this.data = data;
    }
}
