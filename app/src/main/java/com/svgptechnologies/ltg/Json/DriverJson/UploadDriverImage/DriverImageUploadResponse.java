package com.svgptechnologies.ltg.Json.DriverJson.UploadDriverImage;

public class DriverImageUploadResponse {

    private DriverImageUploadData data;

    public DriverImageUploadResponse(DriverImageUploadData data) {
        this.data = data;
    }


    public DriverImageUploadData getData() {
        return data;
    }

    public void setData(DriverImageUploadData data) {
        this.data = data;
    }
}
