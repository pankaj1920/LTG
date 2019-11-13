package com.svgptechnologies.ltg.Json.UserJson.UserUploadImage;

public class UserImageUploadResponse {

    String status;
    UserImageUploadData data;

    public UserImageUploadResponse(String status, UserImageUploadData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserImageUploadData getData() {
        return data;
    }

    public void setData(UserImageUploadData data) {
        this.data = data;
    }
}
