package com.svgptechnologies.ltg.Json.DriverJson.DriverNamePassDialog;

import java.util.List;

public class DriveridPassDialogResponse {


    List<DriverIdPassDialogData> data;

    public DriveridPassDialogResponse(List<DriverIdPassDialogData> data) {
        this.data = data;
    }

    public List<DriverIdPassDialogData> getData() {
        return data;
    }

    public void setData(List<DriverIdPassDialogData> data) {
        this.data = data;
    }
}
