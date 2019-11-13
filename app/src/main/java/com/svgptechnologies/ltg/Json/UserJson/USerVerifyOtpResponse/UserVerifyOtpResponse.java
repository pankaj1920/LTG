package com.svgptechnologies.ltg.Json.UserJson.USerVerifyOtpResponse;

public class UserVerifyOtpResponse {

    public String status;
    public String mobile;
    public String otp;
    public String id;
    public String uid;
    public String data;

    public UserVerifyOtpResponse(String status, String mobile, String otp, String id, String uid, String data) {
        this.status = status;
        this.mobile = mobile;
        this.otp = otp;
        this.id = id;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
