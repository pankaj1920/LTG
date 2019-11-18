package com.svgptechnologies.ltg.Json.UserJson.GetDriverLocation;

import java.util.List;

public class GetDriverLocationResponse {

    private String status;

    private List<GetDriverLocationData> data;

    public GetDriverLocationResponse ( String status, List<GetDriverLocationData> data ) {
        this.status = status;
        this.data = data;
    }


    public String getStatus ( ) {
        return status;
    }

    public void setStatus ( String status ) {
        this.status = status;
    }

    public List<GetDriverLocationData> getData ( ) {
        return data;
    }

    public void setData ( List<GetDriverLocationData> data ) {
        this.data = data;
    }
}
