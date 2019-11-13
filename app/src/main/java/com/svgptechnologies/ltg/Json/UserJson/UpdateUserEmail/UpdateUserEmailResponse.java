package com.svgptechnologies.ltg.Json.UserJson.UpdateUserEmail;

public class UpdateUserEmailResponse {

    String status;
    UpdateUserEmailData data;

    public UpdateUserEmailResponse(String status, UpdateUserEmailData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateUserEmailData getData() {
        return data;
    }

    public void setData(UpdateUserEmailData data) {
        this.data = data;
    }
}
