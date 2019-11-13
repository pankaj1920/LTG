package com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverEmail;

public class DriverUpdateEmailData {

    String status;
    String driver_id;
    String password;

    public DriverUpdateEmailData(String status, String driver_id, String password) {
        this.status = status;
        this.driver_id = driver_id;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
