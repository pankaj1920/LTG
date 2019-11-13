package com.svgptechnologies.ltg.User.UserForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.User.UserLoginActivity;

public class ForgotPaswrdChangdSucesfulyActivity extends AppCompatActivity {

    Button UFPCSbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_paswrd_changd_sucesfuly);

        UFPCSbtn = (Button) findViewById(R.id.UFPCSbtn);

        UFPCSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPaswrdChangdSucesfulyActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}