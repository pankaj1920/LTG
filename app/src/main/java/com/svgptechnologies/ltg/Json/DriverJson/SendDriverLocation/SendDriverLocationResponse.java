package com.svgptechnologies.ltg.Json.DriverJson.SendDriverLocation;

public class SendDriverLocationResponse {

    private SendDriverLocationData data;

    public SendDriverLocationResponse(SendDriverLocationData data) {
        this.data = data;
    }

    public SendDriverLocationData getData() {
        return data;
    }

    public void setData(SendDriverLocationData data) {
        this.data = data;
    }
}
