package com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverName;

public class UpdateDriverNameResponse {


    private UpdateDriverNameData data;

    public UpdateDriverNameResponse(UpdateDriverNameData data) {
        this.data = data;
    }

    public UpdateDriverNameData getData() {
        return data;
    }

    public void setData(UpdateDriverNameData data) {
        this.data = data;
    }
}
