package com.svgptechnologies.ltg.Json.UserJson.GetUserDetail;

import java.util.List;

public class GetUserDetailResponse {

    private String status;
    private List<GetUserDetailData> data;

    public GetUserDetailResponse(String status, List<GetUserDetailData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GetUserDetailData> getData() {
        return data;
    }

    public void setData(List<GetUserDetailData> data) {
        this.data = data;
    }
}
