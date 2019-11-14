package com.svgptechnologies.ltg.Json.DriverJson.CancleBooking;

public class CancleBookingData {

    private String status;
    private String trip_status;
    private String did;

    public CancleBookingData(String status, String trip_status, String did) {
        this.status = status;
        this.trip_status = trip_status;
        this.did = did;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(String trip_status) {
        this.trip_status = trip_status;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
