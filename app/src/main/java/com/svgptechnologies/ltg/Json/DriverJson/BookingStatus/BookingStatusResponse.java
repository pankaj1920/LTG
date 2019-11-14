package com.svgptechnologies.ltg.Json.DriverJson.BookingStatus;

public class BookingStatusResponse {

    private BookingStatusData data;

    public BookingStatusResponse(BookingStatusData data) {
        this.data = data;
    }

    public BookingStatusData getData() {
        return data;
    }

    public void setData(BookingStatusData data) {
        this.data = data;
    }
}
