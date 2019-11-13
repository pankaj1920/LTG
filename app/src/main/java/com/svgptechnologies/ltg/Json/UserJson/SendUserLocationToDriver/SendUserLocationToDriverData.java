package com.svgptechnologies.ltg.Json.UserJson.SendUserLocationToDriver;

public class SendUserLocationToDriverData {


    private String status;
    private String uid;
    private String user_name;
    private String user_lat;
    private String user_lang;
    private String user_address;
    private String pincode;
    private String did;

    public SendUserLocationToDriverData(String status, String uid, String user_name, String user_lat, String user_lang, String user_address, String pincode, String did) {
        this.status = status;
        this.uid = uid;
        this.user_name = user_name;
        this.user_lat = user_lat;
        this.user_lang = user_lang;
        this.user_address = user_address;
        this.pincode = pincode;
        this.did = did;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_lang() {
        return user_lang;
    }

    public void setUser_lang(String user_lang) {
        this.user_lang = user_lang;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}
