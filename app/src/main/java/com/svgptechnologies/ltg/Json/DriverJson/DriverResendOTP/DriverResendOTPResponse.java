package com.svgptechnologies.ltg.Json.DriverJson.DriverResendOTP;

public class DriverResendOTPResponse {

    private String status;
    private String name;
    private String mobile;
    private String otp;
    private String vehicl_no;
    private String data;


    public DriverResendOTPResponse(String status, String name, String mobile, String otp, String vehicl_no, String data) {
        this.status = status;
        this.name = name;
        this.mobile = mobile;
        this.otp = otp;
        this.vehicl_no = vehicl_no;
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

    public String getVehicl_no() {
        return vehicl_no;
    }

    public void setVehicl_no(String vehicl_no) {
        this.vehicl_no = vehicl_no;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
