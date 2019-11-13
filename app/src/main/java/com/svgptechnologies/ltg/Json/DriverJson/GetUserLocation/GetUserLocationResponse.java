package com.svgptechnologies.ltg.Json.DriverJson.GetUserLocation;

import java.util.List;

public class GetUserLocationResponse {

    String status;

    List<GetUserLocationData> data;

    public GetUserLocationResponse(String status, List<GetUserLocationData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GetUserLocationData> getData() {
        return data;
    }

    public void setData(List<GetUserLocationData> data) {
        this.data = data;
    }
}
