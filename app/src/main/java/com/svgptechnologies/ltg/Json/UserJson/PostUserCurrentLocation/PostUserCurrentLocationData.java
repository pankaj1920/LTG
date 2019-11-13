package com.svgptechnologies.ltg.Json.UserJson.PostUserCurrentLocation;

public class PostUserCurrentLocationData {


    String status;
    String name;
    String mobile;
    String lat;
    String lang;
    String address;
    String pincode;
    String latlang;


    public PostUserCurrentLocationData(String status, String name, String mobile, String lat, String lang, String address, String pincode, String latlang) {
        this.status = status;
        this.name = name;
        this.mobile = mobile;
        this.lat = lat;
        this.lang = lang;
        this.address = address;
        this.pincode = pincode;
        this.latlang = latlang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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
}
