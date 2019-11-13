package com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail;

public class UpdateDriverAllDetailData {

    String status;
    String driver_id;
    String name;
    String email;
    String password;
    String address;
    String otp;


    public UpdateDriverAllDetailData(String status, String driver_id, String name, String email, String password, String address, String otp) {
        this.status = status;
        this.driver_id = driver_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.otp = otp;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
