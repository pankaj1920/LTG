package com.svgptechnologies.ltg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.UserLoginActivity;

public class CheckInternetConnectionActivity extends AppCompatActivity {

    Button internetRetryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet_connection);

        internetRetryBtn = findViewById(R.id.internetRetryBtn);

        internetRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkInternetConnection();
            }
        });
    }


    public void checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (UserSharePrefManager.getInstance(CheckInternetConnectionActivity.this).UserAlreadyLoggedIn()) {
                            Intent intent = new Intent(CheckInternetConnectionActivity.this, AllowLocationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(CheckInternetConnectionActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, 2500);

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (UserSharePrefManager.getInstance(CheckInternetConnectionActivity.this).UserAlreadyLoggedIn()) {
                            Intent intent = new Intent(CheckInternetConnectionActivity.this, AllowLocationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(CheckInternetConnectionActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, 2500);

            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_LONG)
                    .setAction("OK", null)
                    .setDuration(5000)
                    .setActionTextColor(Color.WHITE).show();


            return;

        }
    }
}

