package com.svgptechnologies.ltg.Json.DriverJson.SelectServiceType;

public class SelectServiceTypeData {

    String servcie_name;
    String service_image;

    public SelectServiceTypeData(String servcie_name, String service_image) {
        this.servcie_name = servcie_name;
        this.service_image = service_image;
    }


    public String getServcie_name() {
        return servcie_name;
    }

    public void setServcie_name(String servcie_name) {
        this.servcie_name = servcie_name;
    }

    public String getService_image() {
        return service_image;
    }

    public void setService_image(String service_image) {
        this.service_image = service_image;
    }
}
