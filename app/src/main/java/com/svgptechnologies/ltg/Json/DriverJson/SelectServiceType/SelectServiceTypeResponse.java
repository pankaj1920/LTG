package com.svgptechnologies.ltg.Json.DriverJson.SelectServiceType;

import java.util.List;

public class SelectServiceTypeResponse {

    String status;
    List<SelectServiceTypeData> data;

    public SelectServiceTypeResponse(String status, List<SelectServiceTypeData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectServiceTypeData> getData() {
        return data;
    }

    public void setData(List<SelectServiceTypeData> data) {
        this.data = data;
    }
}
