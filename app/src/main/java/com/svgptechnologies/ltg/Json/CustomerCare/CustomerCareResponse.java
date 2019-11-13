package com.svgptechnologies.ltg.Json.CustomerCare;

public class CustomerCareResponse {

    private String status;
    private String id;
    private String language;
    private String name;
    private String email;
    private String mobile;
    private String message;
    private String data;


    public CustomerCareResponse(String status, String id, String language, String name, String email, String mobile, String message, String data) {
        this.status = status;
        this.id = id;
        this.language = language;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.message = message;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
