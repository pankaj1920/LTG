package com.svgptechnologies.ltg.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.svgptechnologies.ltg.Driver.DriverForgotPassword.DriverForgetPasswordActivity;
import com.svgptechnologies.ltg.Driver.DriverRegistration.DriverRigesterActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverLoginActivity extends AppCompatActivity {

    EditText DIdEditText, DPasswordEditText;
    TextView DforgotPassword, DRegisterTextView;
    Button DLoginBtn;
    private String DriverToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);


        DIdEditText = (EditText) findViewById(R.id.DIdEditText);

        DPasswordEditText = (EditText) findViewById(R.id.DPasswordEditText);

        DforgotPassword = (TextView) findViewById(R.id.DforgotPassword);

        DRegisterTextView = (TextView) findViewById(R.id.DRegisterTextView);

        DLoginBtn = (Button) findViewById(R.id.DLoginBtn);

        DforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, DriverForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        DRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, DriverRigesterActivity.class);
                startActivity(intent);
            }
        });

        DLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverLogin();
            }
        });


        // Getting Firebase Token
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                DriverToken = instanceIdResult.getToken();
                Toast.makeText(DriverLoginActivity.this, DriverToken, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (DriverSharePrefManager.getInstance(this).DriverAlreadyLoggedIn()) {
            Intent intent = new Intent(DriverLoginActivity.this, DriverHomePageActivity.class);
            startActivity(intent);
            finish();
        } else {
            return;
        }
    }

    private void DriverLogin() {


        String username = DIdEditText.getText().toString();
        String password = DPasswordEditText.getText().toString();


        if (username.equals("")) {

            DIdEditText.setError("Invalid Email or Number");
            DIdEditText.requestFocus();
            return;

        }
        if (password.equals("")) {

            DPasswordEditText.setError("Invalid Password");
            DPasswordEditText.requestFocus();
            return;

        }

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<DriverLoginResponse> call = ltgApi.DriverLogin(username, password,DriverToken);
        call.enqueue(new Callback<DriverLoginResponse>() {
            @Override
            public void onResponse(Call<DriverLoginResponse> call, Response<DriverLoginResponse> response) {

                DriverLoginResponse loginResponse = response.body();

                if (response.isSuccessful() && loginResponse.getStatus().equals("1")) {

                    //if the login Responwe is sucessfull we will save the user
                    DriverSharePrefManager.getInstance(DriverLoginActivity.this).saveDriverLoginDetail(loginResponse);

                    Toast.makeText(DriverLoginActivity.this, loginResponse.getStatus(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DriverLoginActivity.this, DriverHomePageActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    // Toast.makeText(LoginActivity.this, "unSucessfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverLoginResponse> call, Throwable t) {
                Toast.makeText(DriverLoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverLoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
