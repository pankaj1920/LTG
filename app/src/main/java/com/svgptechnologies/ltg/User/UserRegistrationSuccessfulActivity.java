package com.svgptechnologies.ltg.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.svgptechnologies.ltg.R;

public class UserRegistrationSuccessfulActivity extends AppCompatActivity {

    Button RScontinueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration_successful);


        RScontinueBtn = (Button) findViewById(R.id.RScontinueBtn);

        RScontinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegistrationSuccessfulActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}