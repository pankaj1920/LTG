package com.svgptechnologies.ltg.Json.DriverJson.CancleBooking;

public class CancleBookingResponse {

    private CancleBookingData data;

    public CancleBookingResponse(CancleBookingData data) {
        this.data = data;
    }


    public CancleBookingData getData() {
        return data;
    }

    public void setData(CancleBookingData data) {
        this.data = data;
    }
}
