package com.svgptechnologies.ltg.Json.DriverJson.DriverTripHistory;

import java.util.List;

public class DriverTripHistoryResponse {

    private List<DriverTripHistoryData> data;

    public DriverTripHistoryResponse ( List<DriverTripHistoryData> data ) {
        this.data = data;
    }

    public List<DriverTripHistoryData> getData ( ) {
        return data;
    }

    public void setData ( List<DriverTripHistoryData> data ) {
        this.data = data;
    }
}
