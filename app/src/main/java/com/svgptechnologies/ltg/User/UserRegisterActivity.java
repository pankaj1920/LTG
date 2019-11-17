package com.svgptechnologies.ltg.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AppCompatActivity {


    Button UserRegisterBtn;

    EditText UR_Name, UR_mobile, UR_Email, UR_Password, UR_ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);


        UserRegisterBtn = (Button) findViewById(R.id.UserRegisterBtn);


        UR_Name = (EditText) findViewById(R.id.UR_Name);
        UR_mobile = (EditText) findViewById(R.id.UR_mobile);
        UR_Email = (EditText) findViewById(R.id.UR_Email);
        UR_Password = (EditText) findViewById(R.id.UR_Password);
        UR_ConfirmPassword = (EditText) findViewById(R.id.UR_ConfirmPassword);

        UserRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestReciveSms();

            }
        });
    }


    public void goToVerifyOTPactivity() {

        final String Umobile = UR_mobile.getText().toString();
        final String Uname = UR_Name.getText().toString();
        final String Uemail = UR_Email.getText().toString();
        final String Upassword = UR_Password.getText().toString();
        final String UconfirmPassword = UR_ConfirmPassword.getText().toString();

        if (Umobile.length() < 10 || Umobile.length() > 10) {
            UR_mobile.setError("Enter The Correct Number");
            UR_mobile.requestFocus();
            return;
        }

        if (Uname.equals("")) {
            UR_Name.setError("Enter The Correct Name");
            UR_Name.requestFocus();
            return;
        }

        if (Uemail.equals("")) {
            UR_Email.setError("Enter The Correct Email");
            UR_Email.requestFocus();
            return;
        }

        if (Upassword.equals("")) {
            UR_Password.setError("Enter The Correct Password");
            UR_Password.requestFocus();
            return;
        }

        if (UconfirmPassword.equals("")) {
            UR_ConfirmPassword.setError("Incorrect Password");
            UR_ConfirmPassword.requestFocus();
            return;
        }

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<ResponseBody> call = ltgApi.UserRigestration(Uname, Umobile, Uemail, Upassword, UconfirmPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody responseBody = response.body();

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {


                    try {
                        String s = response.body().string();

                        Intent intent = new Intent(UserRegisterActivity.this, UserVerifyOtpActivity.class);

                        //sending mobile number to Enter OTP Activity so that we can display that number their
                        Bundle bundle = new Bundle();
                        bundle.putString("UmobNum", Umobile);
                        bundle.putString("Uname", Uname);
                        bundle.putString("Uemail", Uemail);
                        bundle.putString("Upassword", Upassword);
                        bundle.putString("Ucpassword", UconfirmPassword);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        //   Toast.makeText(UserRegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                        Toast.makeText(UserRegisterActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(UserRegisterActivity.this, "Unsucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), "Connection Failed", Snackbar.LENGTH_LONG)
                        .setAction("OK", null)
                        .setDuration(5000)
                        .setActionTextColor( Color.WHITE).show();

                Toast.makeText(UserRegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(UserRegisterActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            //  Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void displayPermission(String permission, Integer requestCode) {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                UserRegisterActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(UserRegisterActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(UserRegisterActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    //Runtime Permission so that it can AutoVerify The Otp in EnterOtpActivity
    private void requestReciveSms() {
        if (askForPermission(Manifest.permission.RECEIVE_SMS, 1)) {

            goToVerifyOTPactivity();

        } else {
            displayPermission(Manifest.permission.RECEIVE_SMS, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 1) {
                requestReciveSms();
            }

        }
        // this else statment will run wen user click on dont ask again and deined the permission and still click on allow button
        else {
            Toast.makeText(this, "Enable Message permission from Setting", Toast.LENGTH_SHORT).show();
        }
    }

}
