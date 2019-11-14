package com.svgptechnologies.ltg.Json.DriverJson.SendDriverLocation;

public class SendDriverLocationData {

    private String status;
    private String user_id;
    private String driver_id;
    private String driver_name;
    private String driver_lat;
    private String driver_lang;
    private String driver_address;


    public SendDriverLocationData(String status, String user_id, String driver_id, String driver_name, String driver_lat, String driver_lang, String driver_address) {
        this.status = status;
        this.user_id = user_id;
        this.driver_id = driver_id;
        this.driver_name = driver_name;
        this.driver_lat = driver_lat;
        this.driver_lang = driver_lang;
        this.driver_address = driver_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_lat() {
        return driver_lat;
    }

    public void setDriver_lat(String driver_lat) {
        this.driver_lat = driver_lat;
    }

    public String getDriver_lang() {
        return driver_lang;
    }

    public void setDriver_lang(String driver_lang) {
        this.driver_lang = driver_lang;
    }

    public String getDriver_address() {
        return driver_address;
    }

    public void setDriver_address(String driver_address) {
        this.driver_address = driver_address;
    }
}
