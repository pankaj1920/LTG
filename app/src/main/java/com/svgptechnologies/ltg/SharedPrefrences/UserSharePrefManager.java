package com.svgptechnologies.ltg.SharedPrefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginData;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginResponse;

public class UserSharePrefManager {

    private static UserSharePrefManager sharePrefMamager;
    //to handle we need Context object
    private Context context;

    //Create Constructor
    public UserSharePrefManager(Context context) {
        this.context = context;
    }

    //we will create Syncronized Method as we only want a single instance
    public static synchronized UserSharePrefManager getInstance(Context context) {

        if (sharePrefMamager == null) {   //this mean the object is no yet created in this case we will make new SharedPrefrenceManager
            sharePrefMamager = new UserSharePrefManager(context);
        }
        return sharePrefMamager;
    }


    //now we will create method that will store User Driver login Details
    public void saveUserLoginDetail(UserLoginResponse loginResponse) {

        //here mode is private bcz we only want this application to access shared prefrence
        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("status", loginResponse.getStatus());
        editor.putString("id", loginResponse.getData().getId());
        editor.putString("name", loginResponse.getData().getName());
        editor.putString("mobile", loginResponse.getData().getMobile());
        editor.putString("email", loginResponse.getData().getEmail());
        editor.putString("UserToken", loginResponse.getData().getToken());
        editor.putString("data", loginResponse.getData().getData());

        editor.apply();

        //here we have saved loginDetails in sharePrefrences
    }



    //now we will create one more method to check if the user is already loggedI or not
    //if user detail is already presennt in share prefrence we will assume that user is alreadyu logged in
    public boolean UserAlreadyLoggedIn() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //it mean that value in id is saved bcz in database we cannot put id == -1
        return sharedPreferences.getString("id", "-1") != "-1"; //bcz user is already logged in
    }



    // this method will use to get back USer Driver Detail which is saved in any activty
    //now we need to get back the user and Driver
    public UserLoginData getUserDetail() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);

        //now we can read the value form sharePrefrences object
        UserLoginData userLoginDataDetail = new UserLoginData(
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("mobile", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("UserToken", null),
                sharedPreferences.getString("data", null)

        );

        return userLoginDataDetail;
    }


    //Create a method to Logout
    public void UserLogout() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}