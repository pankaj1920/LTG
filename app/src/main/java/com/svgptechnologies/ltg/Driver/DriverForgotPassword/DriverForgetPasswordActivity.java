package com.svgptechnologies.ltg.Driver.DriverForgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverForgetPasswordActivity extends AppCompatActivity {

    EditText DF_EnterPatnerId;
    Button DF_GetOtpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_forget_password);


        DF_EnterPatnerId = (EditText) findViewById(R.id.DF_EnterPatnerId);
        DF_GetOtpBtn = (Button) findViewById(R.id.DF_GetOtpBtn);


        DF_GetOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestReciveSms();
            }
        });
    }


    private void changeDriverPassword() {

        final String driveId = DF_EnterPatnerId.getText().toString();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<ResponseBody> call = ltgApi.driverForgotPassOtp(driveId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    Toast.makeText(DriverForgetPasswordActivity.this, "Sucess", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DriverForgetPasswordActivity.this, DriverChangePasswordActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("DriverId",driveId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(DriverForgetPasswordActivity.this, "UnSucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(DriverForgetPasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverForgetPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(

                DriverForgetPasswordActivity.this, permission)
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
                DriverForgetPasswordActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(DriverForgetPasswordActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(DriverForgetPasswordActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    //Runtime Permission so that it can AutoVerify The Otp in EnterOtpActivity
    private void requestReciveSms() {
        if (askForPermission(Manifest.permission.RECEIVE_SMS, 1)) {

            changeDriverPassword();

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
