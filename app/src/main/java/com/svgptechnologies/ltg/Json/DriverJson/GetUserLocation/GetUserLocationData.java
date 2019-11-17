package com.svgptechnologies.ltg.Json.DriverJson.GetUserLocation;

public class GetUserLocationData {

    String id;
    String user_name;
    String user_mobile;
    String profile_pic;
    String user_lat;
    String user_lang;
    String user_address;
    String pincode;
    String trip_status;

    public GetUserLocationData ( String id, String user_name, String user_mobile, String profile_pic, String user_lat, String user_lang, String user_address, String pincode, String trip_status ) {
        this.id = id;
        this.user_name = user_name;
        this.user_mobile = user_mobile;
        this.profile_pic = profile_pic;
        this.user_lat = user_lat;
        this.user_lang = user_lang;
        this.user_address = user_address;
        this.pincode = pincode;
        this.trip_status = trip_status;
    }


    public String getId ( ) {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getUser_name ( ) {
        return user_name;
    }

    public void setUser_name ( String user_name ) {
        this.user_name = user_name;
    }

    public String getUser_mobile ( ) {
        return user_mobile;
    }

    public void setUser_mobile ( String user_mobile ) {
        this.user_mobile = user_mobile;
    }

    public String getProfile_pic ( ) {
        return profile_pic;
    }

    public void setProfile_pic ( String profile_pic ) {
        this.profile_pic = profile_pic;
    }

    public String getUser_lat ( ) {
        return user_lat;
    }

    public void setUser_lat ( String user_lat ) {
        this.user_lat = user_lat;
    }

    public String getUser_lang ( ) {
        return user_lang;
    }

    public void setUser_lang ( String user_lang ) {
        this.user_lang = user_lang;
    }

    public String getUser_address ( ) {
        return user_address;
    }

    public void setUser_address ( String user_address ) {
        this.user_address = user_address;
    }

    public String getPincode ( ) {
        return pincode;
    }

    public void setPincode ( String pincode ) {
        this.pincode = pincode;
    }

    public String getTrip_status ( ) {
        return trip_status;
    }

    public void setTrip_status ( String trip_status ) {
        this.trip_status = trip_status;
    }
}
