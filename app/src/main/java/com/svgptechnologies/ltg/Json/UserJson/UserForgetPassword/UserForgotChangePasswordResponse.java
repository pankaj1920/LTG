package com.svgptechnologies.ltg.Json.UserJson.UserForgetPassword;

public class UserForgotChangePasswordResponse {

    private String status;
    private String id;
    private String name;
    private String mobile;
    private String email;
    private String data;


    public UserForgotChangePasswordResponse(String status, String id, String name, String mobile, String email, String data) {
        this.status = status;
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
