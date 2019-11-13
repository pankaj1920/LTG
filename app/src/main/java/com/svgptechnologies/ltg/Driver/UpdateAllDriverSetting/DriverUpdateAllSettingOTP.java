package com.svgptechnologies.ltg.Driver.UpdateAllDriverSetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginData;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail.UpdateAllSettinOTPVerification.UpdateSettingOtpResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverUpdateAllSettingOTP extends AppCompatActivity {

    EditText DUAenterOTp;
    Button DUASotpbtn;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_update_all_setting_otp);



        DUASotpbtn = (Button) findViewById(R.id.DUASotpbtn);

        DUAenterOTp = (EditText) findViewById(R.id.DUAenterOTp);

        upDateDriverDetail();

        DUASotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upDateDriverDetail();
            }
        });
    }

    private void upDateDriverDetail() {

        DriverLoginData loginData = DriverSharePrefManager.getInstance(DriverUpdateAllSettingOTP.this).getDriverDetail();

        String driver_Id = loginData.getDriver_id();

        final String otp = DUAenterOTp.getText().toString().trim();
        Toast.makeText(this, driver_Id, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, otp, Toast.LENGTH_SHORT).show();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<UpdateSettingOtpResponse> call = ltgApi.updateDriverAllSetting(driver_Id, otp);

        call.enqueue(new Callback<UpdateSettingOtpResponse>() {
            @Override
            public void onResponse(Call<UpdateSettingOtpResponse> call, Response<UpdateSettingOtpResponse> response) {

                if (response.isSuccessful()) {

                    UpdateSettingOtpResponse otpResponse = response.body();

                    if (otpResponse.getData() != null) {
                        String status = otpResponse.getData().getStatus();
                        if (status.equals("1")){

                            Toast.makeText(DriverUpdateAllSettingOTP.this, "Status : " + status, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DriverUpdateAllSettingOTP.this, DriverUpdateAllSettingSuccessful.class);
                            startActivity(intent);
                        }else {

                            Snackbar.make(findViewById(android.R.id.content), "OTP Incorrect", Snackbar.LENGTH_LONG)
                                    .setAction("OK", null)
                                    .setDuration(5000)
                                    .setActionTextColor(Color.WHITE).show();
                        }

                    } else {

                        Toast.makeText(DriverUpdateAllSettingOTP.this, "Data is Null", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<UpdateSettingOtpResponse> call, Throwable t) {

            }
        });

    }
}
