package com.svgptechnologies.ltg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.UserLoginActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set splash screen as full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // checkin the internet connection
                checkInternetConnection();

            }
        }, 2500);


    }

    public void checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if (UserSharePrefManager.getInstance(SplashScreen.this).UserAlreadyLoggedIn()) {
                    Intent intent = new Intent(SplashScreen.this, AllowLocationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                if (UserSharePrefManager.getInstance(SplashScreen.this).UserAlreadyLoggedIn()) {
                    Intent intent = new Intent(SplashScreen.this, AllowLocationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        } else {

            Intent intent = new Intent(SplashScreen.this, CheckInternetConnectionActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
