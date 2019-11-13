package com.svgptechnologies.ltg.Driver.DriverForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverForgetPassword.DriverForgotChangePasswordResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverForgorPasswordResendOTP.DriverForgorPasswordResendOTPResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverChangePasswordActivity extends AppCompatActivity {

    String driverId;
    EditText DF_EnterOTp, DF_Password, DF_ConfirmPassword;
    Button DF_Submit;
    TextView DF_ResendOtp, DF_DisableResendOtp, DF_ResendCountDown;

    // starting time 2min
    private static final long START_TIME_IN_MILLI = 120000;

    private CountDownTimer countDownTimer;

    // this will tell is timer is running or not
    private boolean TimmerRunning;

    // here we will start timeLeft intial with start timer
    private long TimeLeftInMillis = START_TIME_IN_MILLI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_change_password);



        DF_EnterOTp = (EditText) findViewById(R.id.DF_EnterOTp);
        DF_Password = (EditText) findViewById(R.id.DF_Password);
        DF_ConfirmPassword = (EditText) findViewById(R.id.DF_ConfirmPassword);
        DF_ResendOtp = (TextView) findViewById(R.id.DF_ResendOtp);
        DF_DisableResendOtp = (TextView) findViewById(R.id.DF_DisableResendOtp);
        DF_ResendCountDown = (TextView) findViewById(R.id.DF_ResendCountDown);
        DF_Submit = (Button) findViewById(R.id.DF_Submit);

        // starting the resend Timer
        startResendTimer();

        Bundle bundle = getIntent().getExtras();
        driverId = bundle.getString("DriverId");

        DF_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeDriverForgotPassword();
            }
        });

        DF_ResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendOTP();

                startResendTimer();
            }
        });

    }

    private void changeDriverForgotPassword() {

        Bundle bundle = getIntent().getExtras();
        String driverID = bundle.getString("DriverId");
        String otp = DF_EnterOTp.getText().toString();
        String password = DF_Password.getText().toString();
        String cPassword = DF_ConfirmPassword.getText().toString();

        Toast.makeText(this, driverID, Toast.LENGTH_SHORT).show();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<DriverForgotChangePasswordResponse> call = ltgApi.driverFogetChangePassword(driverID, otp, password, cPassword);

        call.enqueue(new Callback<DriverForgotChangePasswordResponse>() {
            @Override
            public void onResponse(Call<DriverForgotChangePasswordResponse> call, Response<DriverForgotChangePasswordResponse> response) {

                DriverForgotChangePasswordResponse forgotChangePasswordResponse = response.body();
                String status = forgotChangePasswordResponse.getStatus();

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && status.equals("1")) {

                    Toast.makeText(DriverChangePasswordActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DriverChangePasswordActivity.this, DriverForgtPswrdChngSucsfulyActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Snackbar.make(findViewById(android.R.id.content), "OTP Incorrect", Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .setDuration(5000)
                            .setActionTextColor(Color.WHITE).show();
                }
            }

            @Override
            public void onFailure(Call<DriverForgotChangePasswordResponse> call, Throwable t) {

                Toast.makeText(DriverChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //To Resend Otp
    private void resendOTP() {

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<DriverForgorPasswordResendOTPResponse> call = ltgApi.driverResendForgotPassOtp(driverId);
        call.enqueue(new Callback<DriverForgorPasswordResendOTPResponse>() {
            @Override
            public void onResponse(Call<DriverForgorPasswordResendOTPResponse> call, Response<DriverForgorPasswordResendOTPResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    Snackbar.make(findViewById(android.R.id.content), "OTP Sent Sucessfully", Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .setDuration(5000)
                            .setActionTextColor(Color.WHITE).show();

                } else {

                    Toast.makeText(DriverChangePasswordActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverForgorPasswordResendOTPResponse> call, Throwable t) {

                Toast.makeText(DriverChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    // here wwe are give timer of two min after that resend Button will be visible
    public void startResendTimer() {

// here we have to give two  parameter 1st one is TimeLett and 2nd is mill second after which the onTick method is called
        countDownTimer = new CountDownTimer(TimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                TimeLeftInMillis = millisUntilFinished;

                // it will convert sec in minute
                int minute = (int) (TimeLeftInMillis / 1000) / 60;

                // here we will get remaining second after getting minute
                int second = (int) (TimeLeftInMillis / 1000) % 60;

                String timeLEftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minute, second);
                DF_ResendCountDown.setText(timeLEftFormatted);
            }

            @Override
            public void onFinish() {

                // here timmer is finished
                TimmerRunning = false;
                // bcz we cannot start time again if timer is 0 we have to do reset
                DF_DisableResendOtp.setVisibility(View.INVISIBLE);

                DF_ResendCountDown.setVisibility(View.GONE);

                DF_ResendOtp.setVisibility(View.VISIBLE);

            }
        }.start();

        TimmerRunning = true;
    }


}
