package com.svgptechnologies.ltg.Json.UserJson.GetDriverLocation;

public class GetDriverLocationData {

    private String id;
    private String driver_name;
    private String driver_mobile;
    private String driver_lat;
    private String driver_lang;
    private String driver_address;
    private String pincode;
    private String trip_status;


    public String getId ( ) {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getDriver_name ( ) {
        return driver_name;
    }

    public void setDriver_name ( String driver_name ) {
        this.driver_name = driver_name;
    }

    public String getDriver_mobile ( ) {
        return driver_mobile;
    }

    public void setDriver_mobile ( String driver_mobile ) {
        this.driver_mobile = driver_mobile;
    }

    public String getDriver_lat ( ) {
        return driver_lat;
    }

    public void setDriver_lat ( String driver_lat ) {
        this.driver_lat = driver_lat;
    }

    public String getDriver_lang ( ) {
        return driver_lang;
    }

    public void setDriver_lang ( String driver_lang ) {
        this.driver_lang = driver_lang;
    }

    public String getDriver_address ( ) {
        return driver_address;
    }

    public void setDriver_address ( String driver_address ) {
        this.driver_address = driver_address;
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

    public GetDriverLocationData ( String id, String driver_name, String driver_mobile, String driver_lat, String driver_lang, String driver_address, String pincode, String trip_status ) {
        this.id = id;
        this.driver_name = driver_name;
        this.driver_mobile = driver_mobile;
        this.driver_lat = driver_lat;
        this.driver_lang = driver_lang;
        this.driver_address = driver_address;
        this.pincode = pincode;
        this.trip_status = trip_status;




    }
}



