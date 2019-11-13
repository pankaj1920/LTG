package com.svgptechnologies.ltg.Json.UserJson.UpdateUserPassword;

public class UpdateUserPasswordResponse {

    String status;
    UpdateUserPasswordData data;

    public UpdateUserPasswordResponse(String status, UpdateUserPasswordData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateUserPasswordData getData() {
        return data;
    }

    public void setData(UpdateUserPasswordData data) {
        this.data = data;
    }
}
