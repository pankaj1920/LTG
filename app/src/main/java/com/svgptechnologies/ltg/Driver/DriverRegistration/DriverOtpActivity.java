package com.svgptechnologies.ltg.Driver.DriverRegistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverResendOTP.DriverResendOTPResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverVerifyOtpResponse.DriverVerifyOtpRespone;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverOtpActivity extends AppCompatActivity {


    Button Driver_otp_verify;
    TextView DriverResendOtp, driverOtpMobile, DriverDisableResendOtp, DriverResendCountDown;
    EditText DriverenterOtp;
    String DriverToken;

    private String DMobileNum, DriverId;
    String OtpGenerated;
    private static final String OTP_REGEX = "[0-9]{1,6}";

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
        setContentView(R.layout.activity_driver_otp);



        Driver_otp_verify = (Button) findViewById(R.id.Driver_otp_verify);

        DriverResendOtp = (TextView) findViewById(R.id.DriverResendOtp);

        driverOtpMobile = (TextView) findViewById(R.id.driverOtpMobile);

        DriverDisableResendOtp = (TextView) findViewById(R.id.DriverDisableResendOtp);

        DriverResendCountDown = (TextView) findViewById(R.id.DriverResendCountDown);

        DriverenterOtp = (EditText) findViewById(R.id.DriverenterOtp);

        DriverenterOtp.setText(DMobileNum);

        // Start Resend Timer
        startResendTimer();


        //Getting mobile number from Enter Mobile Number Activity and Setting in Enter Otp Activity
        Bundle bundle = getIntent().getExtras();
        DMobileNum = bundle.getString("Dmobile");
        DriverId = bundle.getString("DriverId");

        driverOtpMobile.setText(DMobileNum);



        Driver_otp_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DriverenterOtp.getText().toString().equals(OtpGenerated)) {

                } else {

                    VerifyOTP();
                }
            }
        });

        DriverResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendOTP();

                // as user will click resend again timer will start
                startResendTimer();

                DriverDisableResendOtp.setVisibility(View.VISIBLE);

                DriverResendCountDown.setVisibility(View.VISIBLE);

                DriverResendOtp.setVisibility(View.INVISIBLE);

            }
        });

    }

    private void VerifyOTP() {

        String otp = DriverenterOtp.getText().toString();

        Toast.makeText(this, DriverId, Toast.LENGTH_SHORT).show();

//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//
//
//                // Get new Instance ID token
//                DriverToken = instanceIdResult.getToken();
//            }
//        });

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<DriverVerifyOtpRespone> call = ltgApi.DriverVerifyOtp(DriverId, otp, " Enter Driver Token");
        call.enqueue(new Callback<DriverVerifyOtpRespone>() {
            @Override
            public void onResponse(Call<DriverVerifyOtpRespone> call, Response<DriverVerifyOtpRespone> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    DriverVerifyOtpRespone verifyOtpResponse = response.body();
                    String status = verifyOtpResponse.getStatus();
                    String driverId = verifyOtpResponse.getDriver_id();
                    String password = verifyOtpResponse.getPassword();

                    if (status.equals("1")) {

                        Toast.makeText(DriverOtpActivity.this, "Sucessfull", Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(DriverOtpActivity.this, DriverRegistrationSuccessfulActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("DriverId", driverId);
                        bundle.putString("password", password);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(DriverOtpActivity.this, "UnSucess", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(DriverOtpActivity.this, "OTP Incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverVerifyOtpRespone> call, Throwable t) {
                Toast.makeText(DriverOtpActivity.this, "OTP Incorrect", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //To Resend Otp
    private void resendOTP() {

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<DriverResendOTPResponse> call = ltgApi.resendOtpToDriver(DriverId);
        call.enqueue(new Callback<DriverResendOTPResponse>() {
            @Override
            public void onResponse(Call<DriverResendOTPResponse> call, Response<DriverResendOTPResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    Toast.makeText(DriverOtpActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(DriverOtpActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverResendOTPResponse> call, Throwable t) {

                Toast.makeText(DriverOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                DriverResendCountDown.setText(timeLEftFormatted);
            }

            @Override
            public void onFinish() {

                // here timmer is finished
                TimmerRunning = false;
                // bcz we cannot start time again if timer is 0 we have to do reset
                DriverDisableResendOtp.setVisibility(View.INVISIBLE);

                DriverResendCountDown.setVisibility(View.GONE);

                DriverResendOtp.setVisibility(View.VISIBLE);

                TimeLeftInMillis = START_TIME_IN_MILLI;


            }
        }.start();

        TimmerRunning = true;
    }

}

