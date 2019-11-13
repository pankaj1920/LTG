package com.svgptechnologies.ltg.Json.DriverJson.PostDriverCurrentLocation;


public class PostDriverCurrentLocationResponse {


    public PostDriverCurrentLocationData data;

    public PostDriverCurrentLocationResponse(PostDriverCurrentLocationData data) {
        this.data = data;
    }

    public PostDriverCurrentLocationData getData() {
        return data;
    }

    public void setData(PostDriverCurrentLocationData data) {
        this.data = data;
    }
}
