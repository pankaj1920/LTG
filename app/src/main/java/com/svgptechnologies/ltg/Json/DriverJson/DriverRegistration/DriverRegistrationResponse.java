package com.svgptechnologies.ltg.Json.DriverJson.DriverRegistration;

public class DriverRegistrationResponse {
    public String status;
    public String id;
    public String name;
    public String driver_id;
    public String mobile;
    public String driver_contact;
    public String data;


    public DriverRegistrationResponse(String status, String id, String name, String driver_id, String mobile, String driver_contact, String data) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.driver_id = driver_id;
        this.mobile = mobile;
        this.driver_contact = driver_contact;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDriver_contact() {
        return driver_contact;
    }

    public void setDriver_contact(String driver_contact) {
        this.driver_contact = driver_contact;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
