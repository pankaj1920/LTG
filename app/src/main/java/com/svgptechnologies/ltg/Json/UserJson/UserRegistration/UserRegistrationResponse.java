package com.svgptechnologies.ltg.Json.UserJson.UserRegistration;

public class UserRegistrationResponse {

    public String status;
    public String id;
    public String uid;
    public String name;
    public String email;
    public String mobile;
    public String data;

    public UserRegistrationResponse(String status, String id, String uid, String name, String email, String mobile, String data) {
        this.status = status;
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
