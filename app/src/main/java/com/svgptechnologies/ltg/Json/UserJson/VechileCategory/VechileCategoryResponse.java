package com.svgptechnologies.ltg.Json.UserJson.VechileCategory;

import java.util.List;

public class VechileCategoryResponse {

    private String status;
    private List<VichelCategoryData> data;

    public VechileCategoryResponse(String status, List<VichelCategoryData> data) {
        this.status = status;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VichelCategoryData> getData() {
        return data;
    }

    public void setData(List<VichelCategoryData> data) {
        this.data = data;
    }
}
