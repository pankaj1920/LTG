package com.svgptechnologies.ltg.Json.DriverJson.GetDriverDetails;

import java.util.List;

public class GetDriverDetailsResponse {


    List<GetDriverDetailData> data;

    public GetDriverDetailsResponse(List<GetDriverDetailData> data) {
        this.data = data;
    }

    public List<GetDriverDetailData> getData() {
        return data;
    }

    public void setData(List<GetDriverDetailData> data) {
        this.data = data;
    }
}
