package com.svgptechnologies.ltg.Json.UserJson.SearchDriver;

import java.util.List;

public class SearchDriverResponse {

   List<SearchDriverData> data;

    public SearchDriverResponse(List<SearchDriverData> data) {
        this.data = data;
    }


    public List<SearchDriverData> getData() {
        return data;
    }

    public void setData(List<SearchDriverData> data) {
        this.data = data;
    }
}
