package com.svgptechnologies.ltg.Json.DriverJson.VichelDocument;

import java.util.List;

public class DocumentListResponse {

    private String status;
    private List<DocumentListData> data;

    public DocumentListResponse ( String status, List<DocumentListData> data ) {
        this.status = status;
        this.data = data;
    }


    public String getStatus ( ) {
        return status;
    }

    public void setStatus ( String status ) {
        this.status = status;
    }

    public List<DocumentListData> getData ( ) {
        return data;
    }

    public void setData ( List<DocumentListData> data ) {
        this.data = data;
    }
}
