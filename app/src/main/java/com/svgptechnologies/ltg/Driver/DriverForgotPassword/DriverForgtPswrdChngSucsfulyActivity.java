package com.svgptechnologies.ltg.Driver.DriverForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.svgptechnologies.ltg.Driver.DriverLoginActivity;
import com.svgptechnologies.ltg.R;

public class DriverForgtPswrdChngSucsfulyActivity extends AppCompatActivity {

    Button DFPCSbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_forgt_pswrd_chng_sucsfuly);


        DFPCSbtn = (Button) findViewById(R.id.DFPCSbtn);

        DFPCSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DriverForgtPswrdChngSucsfulyActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
