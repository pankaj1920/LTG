package com.svgptechnologies.ltg.Json.UserJson.UserForgotPasswordResendOtp;

public class UserResendforgotPasswordData {

    String status;
    String mobile;
    String otp;

    public UserResendforgotPasswordData(String status, String mobile, String otp) {
        this.status = status;
        this.mobile = mobile;
        this.otp = otp;
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
}
