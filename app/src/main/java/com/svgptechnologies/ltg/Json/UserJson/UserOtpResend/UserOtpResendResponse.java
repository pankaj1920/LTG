package com.svgptechnologies.ltg.Json.UserJson.UserOtpResend;

public class UserOtpResendResponse {

    private String status;
    private String name;
    private String mobile;
    private String otp;
    private String data;


    public UserOtpResendResponse(String status, String name, String mobile, String otp, String data) {
        this.status = status;
        this.name = name;
        this.mobile = mobile;
        this.otp = otp;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
