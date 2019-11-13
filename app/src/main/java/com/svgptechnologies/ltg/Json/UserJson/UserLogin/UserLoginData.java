package com.svgptechnologies.ltg.Json.UserJson.UserLogin;

public class UserLoginData {
    private String id;
    private String name;
    private String mobile;
    private String email;
    private String token;
    private String data;

    public UserLoginData(String id, String name, String mobile, String email, String token, String data) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.token = token;
        this.data = data;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}