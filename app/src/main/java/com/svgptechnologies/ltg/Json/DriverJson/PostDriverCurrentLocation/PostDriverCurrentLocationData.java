package com.svgptechnologies.ltg.Json.DriverJson.PostDriverCurrentLocation;

public class PostDriverCurrentLocationData {


    String status;
    String driver_id;
    String mobile;
    String lattitude;
    String longitude;
    String address;
    String pincode;
    String latlang;
    String name;
    String vehi_type;

    public PostDriverCurrentLocationData(String status, String driver_id, String mobile, String lattitude, String longitude, String address, String pincode, String latlang, String name, String vehi_type) {
        this.status = status;
        this.driver_id = driver_id;
        this.mobile = mobile;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.address = address;
        this.pincode = pincode;
        this.latlang = latlang;
        this.name = name;
        this.vehi_type = vehi_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLatlang() {
        return latlang;
    }

    public void setLatlang(String latlang) {
        this.latlang = latlang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehi_type() {
        return vehi_type;
    }

    public void setVehi_type(String vehi_type) {
        this.vehi_type = vehi_type;
    }
}
