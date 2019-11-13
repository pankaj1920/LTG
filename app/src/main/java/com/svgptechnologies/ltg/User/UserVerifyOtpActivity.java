package com.svgptechnologies.ltg.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.USerVerifyOtpResponse.UserVerifyOtpResponse;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserVerifyOtpActivity extends AppCompatActivity {


    EditText enterUserOtp;
    TextView ResendOtp, userOtpMobile, DisableResendOtp, ResendCountDown;
    Button user_otp_verify;
    private String UMobileNum, UName, Uemail, Upassword, Ucpassword;
    private static final String OTP_REGEX = "[0-9]{1,6}";

    // starting time 2min
    private static final long START_TIME_IN_MILLI = 120000;

    private CountDownTimer countDownTimer;

    // this will tell is timer is running or not
    private boolean TimmerRunning;

    // here we will start timeLeft intial with start timer
    private long TimeLeftInMillis = START_TIME_IN_MILLI;


    String OtpGenerated;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verify_otp);



        user_otp_verify = (Button) findViewById(R.id.user_otp_verify);

        enterUserOtp = (EditText) findViewById(R.id.enterUserOtp);

        ResendOtp = (TextView) findViewById(R.id.ResendOtp);

        DisableResendOtp = (TextView) findViewById(R.id.DisableResendOtp);

        ResendCountDown = (TextView) findViewById(R.id.ResendCountDown);

        userOtpMobile = (TextView) findViewById(R.id.userOtpMobile);

        userOtpMobile.setText(UMobileNum);

        // Start Resend Timer
        startResendTimer();

        //Getting mobile number from Enter Mobile Number Activity and Setting in Enter Otp Activity
        Bundle bundle = getIntent().getExtras();
        UMobileNum = bundle.getString("UmobNum");
        UName = bundle.getString("Uname");
        Uemail = bundle.getString("Uemail");
        Upassword = bundle.getString("Upassword");
        Ucpassword = bundle.getString("Ucpassword");


        userOtpMobile.setText(UMobileNum + ".");


        user_otp_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (enterUserOtp.getText().toString().equals(OtpGenerated)) {

                } else {

                    VerifyOtp();
                }
            }
        });

        ResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendOTP();
            }
        });
    }


    private void VerifyOtp() {


        String otp = enterUserOtp.getText().toString();

       // String User_Token = FirebaseInstanceId.getInstance().getToken();

   //     Toast.makeText(this, User_Token, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, UMobileNum, Toast.LENGTH_SHORT).show();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<UserVerifyOtpResponse> call = ltgApi.verifyOtp(UMobileNum, otp, "Enter Token");

        call.enqueue(new Callback<UserVerifyOtpResponse>() {
            @Override
            public void onResponse(Call<UserVerifyOtpResponse> call, Response<UserVerifyOtpResponse> response) {
                if (response.isSuccessful() && response.body().getStatus().equals("1")) {

                    UserVerifyOtpResponse verifyOtpResponse = response.body();
                    String s = verifyOtpResponse.getStatus();

                    Toast.makeText(UserVerifyOtpActivity.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                    Toast.makeText(UserVerifyOtpActivity.this, s, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserVerifyOtpActivity.this, UserRegistrationSuccessfulActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(UserVerifyOtpActivity.this, "OTP Incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserVerifyOtpResponse> call, Throwable t) {
                Toast.makeText(UserVerifyOtpActivity.this, "OTP Incorrect", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserVerifyOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //To Resend Otp
    private void resendOTP() {

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<UserVerifyOtpResponse> call = ltgApi.resendUserOTp(UMobileNum);
        call.enqueue(new Callback<UserVerifyOtpResponse>() {
            @Override
            public void onResponse(Call<UserVerifyOtpResponse> call, Response<UserVerifyOtpResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    Snackbar.make(findViewById(android.R.id.content), "OTP Sent Sucessfully", Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .setDuration(5000)
                            .setActionTextColor(Color.WHITE).show();

                } else {

                    Toast.makeText(UserVerifyOtpActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserVerifyOtpResponse> call, Throwable t) {

                Toast.makeText(UserVerifyOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserVerifyOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
//        Call<ResponseBody> call = ltgApi.UserRigestration(UName, UMobileNum, Uemail, Upassword, Ucpassword);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                if (response.isSuccessful()) {
//
//                    try {
//
//                        String s = response.body().string();
//
//                        Snackbar.make(findViewById(android.R.id.content), "OTP Sent Sucessfully", Snackbar.LENGTH_LONG)
//                                .setAction("OK", null)
//                                .setDuration(5000)
//                                .setActionTextColor(Color.WHITE).show();
//
//                        // Toast.makeText(UserVerifyOtpActivity.this, s, Toast.LENGTH_SHORT).show();
//
//                    } catch (IOException e) {
//
//                        e.printStackTrace();
//
//                    }
//                } else {
//                    Toast.makeText(UserVerifyOtpActivity.this, "unSucessfull", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                Toast.makeText(UserVerifyOtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
//                Toast.makeText(UserVerifyOtpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
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
                ResendCountDown.setText(timeLEftFormatted);
            }

            @Override
            public void onFinish() {

                // here timmer is finished
                TimmerRunning = false;
                // bcz we cannot start time again if timer is 0 we have to do reset
                DisableResendOtp.setVisibility(View.INVISIBLE);

                ResendCountDown.setVisibility(View.GONE);

                ResendOtp.setVisibility(View.VISIBLE);

            }
        }.start();

        TimmerRunning = true;
    }


}
