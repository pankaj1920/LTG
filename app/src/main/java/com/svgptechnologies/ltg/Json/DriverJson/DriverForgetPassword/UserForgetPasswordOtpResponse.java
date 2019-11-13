package com.svgptechnologies.ltg.Json.DriverJson.DriverForgetPassword;

public class UserForgetPasswordOtpResponse {

    private String status;
    private String otp;
    private String data;

    public UserForgetPasswordOtpResponse(String status, String otp, String data) {
        this.status = status;
        this.otp = otp;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
