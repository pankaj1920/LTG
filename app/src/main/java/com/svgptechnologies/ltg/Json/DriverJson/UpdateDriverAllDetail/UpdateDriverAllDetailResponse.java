package com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail;


public class UpdateDriverAllDetailResponse {

    UpdateDriverAllDetailData data;

    public UpdateDriverAllDetailResponse(UpdateDriverAllDetailData data) {
        this.data = data;
    }


    public UpdateDriverAllDetailData getData() {
        return data;
    }

    public void setData(UpdateDriverAllDetailData data) {
        this.data = data;
    }
}
