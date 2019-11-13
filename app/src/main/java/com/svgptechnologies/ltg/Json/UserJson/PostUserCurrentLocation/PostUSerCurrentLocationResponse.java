package com.svgptechnologies.ltg.Json.UserJson.PostUserCurrentLocation;

public class PostUSerCurrentLocationResponse {


    public PostUserCurrentLocationData data;

    public PostUSerCurrentLocationResponse(PostUserCurrentLocationData data) {
        this.data = data;
    }


    public PostUserCurrentLocationData getData() {
        return data;
    }

    public void setData(PostUserCurrentLocationData data) {
        this.data = data;
    }
}
