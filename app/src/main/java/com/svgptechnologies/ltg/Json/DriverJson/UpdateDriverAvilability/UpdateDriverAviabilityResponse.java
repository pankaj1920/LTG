package com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAvilability;

public class UpdateDriverAviabilityResponse {

    String status;
    String driver_id;
    String data;

    public UpdateDriverAviabilityResponse(String status, String driver_id, String data) {
        this.status = status;
        this.driver_id = driver_id;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
