package com.svgptechnologies.ltg.Json.UserJson.UserLogin;

public class UserLoginResponse {

    private String status;
    private UserLoginData data;

    public UserLoginResponse(String status, UserLoginData data) {
        this.status = status;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserLoginData getData() {
        return data;
    }

    public void setData(UserLoginData data) {
        this.data = data;
    }
}