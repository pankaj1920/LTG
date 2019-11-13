package com.svgptechnologies.ltg.Json.DriverJson.DriverVerifyOtpResponse;

public class DriverVerifyOtpRespone {

    public String status;
    public String mobile;
    public String otp;
    public String id;
    public String driver_id;
    public String password;
    public String data;

    public DriverVerifyOtpRespone(String status, String mobile, String otp, String id, String driver_id, String password, String data) {
        this.status = status;
        this.mobile = mobile;
        this.otp = otp;
        this.id = id;
        this.driver_id = driver_id;
        this.password = password;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
