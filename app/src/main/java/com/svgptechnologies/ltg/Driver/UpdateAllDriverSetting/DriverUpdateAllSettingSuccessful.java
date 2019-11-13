package com.svgptechnologies.ltg.Driver.UpdateAllDriverSetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;
import com.svgptechnologies.ltg.User.UserHomePageActivity;

public class DriverUpdateAllSettingSuccessful extends AppCompatActivity {

    Button DASUsucessfullbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_update_all_setting_successful);



        DASUsucessfullbtn = (Button) findViewById(R.id.DASUsucessfullbtn);

        DASUsucessfullbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DriverSignout();
            }
        });

    }

    public void DriverSignout() {
        //we are callin Logout Method from SharePrefManager will will delet all user detail from share prefrences
        DriverSharePrefManager.getInstance(DriverUpdateAllSettingSuccessful.this).DriverLogout();
        Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DriverUpdateAllSettingSuccessful.this, UserHomePageActivity.class);
        startActivity(intent);
    }
}
