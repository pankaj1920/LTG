package com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverName;

public class UpdateDriverNameData {

    private String status;
    private String driver_id;
    private String name;

    public UpdateDriverNameData(String status, String driver_id, String name) {
        this.status = status;
        this.driver_id = driver_id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
