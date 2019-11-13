package com.svgptechnologies.ltg.Json.DriverJson.DriverForgetPassword;

public class DriverForgotChangePasswordResponse {

   String status;
   String id;
   String name;
   String mobile;
   String email;
   String vehicle_no;
   String data;

    public DriverForgotChangePasswordResponse(String status, String id, String name, String mobile, String email, String vehicle_no, String data) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.vehicle_no = vehicle_no;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
