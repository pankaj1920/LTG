package com.svgptechnologies.ltg.SharedPrefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginData;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginResponse;

public class DriverSharePrefManager {

    private static DriverSharePrefManager sharePrefMamager;
    //to handle we need Context object
    private Context context;
    //Create Constructor


    public DriverSharePrefManager(Context context) {
        this.context = context;
    }

    //we will create Syncronized Method as we only want a single instance
    public static synchronized DriverSharePrefManager getInstance(Context context) {

        if (sharePrefMamager == null) {   //this mean the object is no yet created in this case we will make new SharedPrefrenceManager
            sharePrefMamager = new DriverSharePrefManager(context);
        }
        return sharePrefMamager;
    }


    //now we will create method that will store User Driver login Details
    public void saveDriverLoginDetail(DriverLoginResponse loginResponse) {

        //here mode is private bcz we only want this application to access shared prefrence
        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Dstatus", loginResponse.getStatus());
        editor.putString("Did", loginResponse.getData().getId());
        editor.putString("DiverLoginId", loginResponse.getData().getDriver_id());
        editor.putString("Dname", loginResponse.getData().getName());
        editor.putString("Dmobile", loginResponse.getData().getMobile());
        editor.putString("Demail", loginResponse.getData().getEmail());
        editor.putString("DToken", loginResponse.getData().getToken());
        editor.putString("Ddata", loginResponse.getData().getData());

        editor.apply();

        //here we have saved loginDetails in sharePrefrences
    }


    //now we will create one more method to check if the user is already loggedI or not
    //if user detail is already presennt in share prefrence we will assume that user is alreadyu logged in
    public boolean DriverAlreadyLoggedIn() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //it mean that value in id is saved bcz in database we cannot put id == -1
        return sharedPreferences.getString("Did", "-1") != "-1"; //bcz user is already logged in
    }


    // this method will use to get back USer Driver Detail which is saved in any activty
    //now we need to get back the user and Driver
    public DriverLoginData getDriverDetail() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //now we can read the value form sharePrefrences object
        DriverLoginData driverLoginDataDetail = new DriverLoginData(
                sharedPreferences.getString("Did", null),
                sharedPreferences.getString("DiverLoginId", null),
                sharedPreferences.getString("Dname", null),
                sharedPreferences.getString("Dmobile", null),
                sharedPreferences.getString("Demail", null),
                sharedPreferences.getString("DToken", null),
                sharedPreferences.getString("Ddata", null)

        );

        return driverLoginDataDetail;
    }


    //Create a method to Logout
    public void DriverLogout() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}