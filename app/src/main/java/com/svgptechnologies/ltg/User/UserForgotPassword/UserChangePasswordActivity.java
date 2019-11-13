package com.svgptechnologies.ltg.User.UserForgotPassword;

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
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.UserForgetPassword.UserForgotChangePasswordResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserForgotPasswordResendOtp.UserResendforgotPasswordResponse;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChangePasswordActivity extends AppCompatActivity {


    Button UF_Submit;
    EditText UF_EnterOTp, UF_Password, UF_ConfirmPassword;
    String mobNum;
    TextView UF_ResendOtp, UF_DisableResendOtp, UF_ResendCountDown;

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
        setContentView(R.layout.activity_user_change_password);


        UF_Submit = (Button) findViewById(R.id.UF_Submit);

        UF_EnterOTp = (EditText) findViewById(R.id.UF_EnterOTp);

        UF_Password = (EditText) findViewById(R.id.UF_Password);

        UF_ResendOtp = (TextView) findViewById(R.id.UF_ResendOtp);

        UF_DisableResendOtp = (TextView) findViewById(R.id.UF_DisableResendOtp);

        UF_ResendCountDown = (TextView) findViewById(R.id.UF_ResendCountDown);

        UF_ConfirmPassword = (EditText) findViewById(R.id.UF_ConfirmPassword);

        Bundle bundle = getIntent().getExtras();
        mobNum = bundle.getString("UF_MobNum");

        // Start Resend Timer
        startResendTimer();


        UF_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeUserForgotPassword();

            }
        });

        UF_ResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendOTP();

                startResendTimer();
            }
        });


    }

    private void changeUserForgotPassword() {

        String otp = UF_EnterOTp.getText().toString();
        String password = UF_Password.getText().toString();
        String Cpassword = UF_ConfirmPassword.getText().toString();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<UserForgotChangePasswordResponse> call = ltgApi.userFogetChangePassword(mobNum, otp, password, Cpassword, mobNum);

        call.enqueue(new Callback<UserForgotChangePasswordResponse>() {
            @Override
            public void onResponse(Call<UserForgotChangePasswordResponse> call, Response<UserForgotChangePasswordResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    UserForgotChangePasswordResponse forgotChangePasswordResponse = response.body();

                    String status = forgotChangePasswordResponse.getStatus();

                    if (status.equals("1")){

                        Intent intent = new Intent(UserChangePasswordActivity.this, ForgotPaswrdChangdSucesfulyActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(UserChangePasswordActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                    }else {

                        Snackbar.make(findViewById(android.R.id.content), "OTP Incorrect", Snackbar.LENGTH_LONG)
                                .setAction("OK", null)
                                .setDuration(5000)
                                .setActionTextColor(Color.WHITE).show();


                    }


                } else {
                    Toast.makeText(UserChangePasswordActivity.this, "UnSuccesful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserForgotChangePasswordResponse> call, Throwable t) {

                Toast.makeText(UserChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //To Resend Otp
    private void resendOTP() {

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<UserResendforgotPasswordResponse> call = ltgApi.userForgotPasswordResendOtp(mobNum);
        call.enqueue(new Callback<UserResendforgotPasswordResponse>() {
            @Override
            public void onResponse(Call<UserResendforgotPasswordResponse> call, Response<UserResendforgotPasswordResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    Snackbar.make(findViewById(android.R.id.content), "OTP Sent Sucessfully", Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .setDuration(5000)
                            .setActionTextColor(Color.WHITE).show();

                } else {

                    Toast.makeText(UserChangePasswordActivity.this, "Unsuccess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResendforgotPasswordResponse> call, Throwable t) {

                Toast.makeText(UserChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                UF_ResendCountDown.setText(timeLEftFormatted);
            }

            @Override
            public void onFinish() {

                // here timmer is finished
                TimmerRunning = false;
                // bcz we cannot start time again if timer is 0 we have to do reset
                UF_DisableResendOtp.setVisibility(View.INVISIBLE);

                UF_ResendCountDown.setVisibility(View.GONE);

                UF_ResendOtp.setVisibility(View.VISIBLE);

            }
        }.start();

        TimmerRunning = true;
    }

}


