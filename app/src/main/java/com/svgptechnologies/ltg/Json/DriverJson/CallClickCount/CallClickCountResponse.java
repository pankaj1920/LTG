package com.svgptechnologies.ltg.Json.DriverJson.CallClickCount;

public class CallClickCountResponse {


    private CallClickCountData data;

    public CallClickCountResponse(CallClickCountData data) {
        this.data = data;
    }

    public CallClickCountData getData() {
        return data;
    }

    public void setData(CallClickCountData data) {
        this.data = data;
    }
}
