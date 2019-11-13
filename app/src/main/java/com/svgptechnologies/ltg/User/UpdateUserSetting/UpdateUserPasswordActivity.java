package com.svgptechnologies.ltg.User.UpdateUserSetting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.UpdateUserPassword.UpdateUserPasswordResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginData;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserPasswordActivity extends AppCompatActivity {

    EditText UUPassword, UUPConfirmPassword;
    Button UUPasswordBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_password);


        UUPassword = (EditText) findViewById(R.id.UUPassword);

        UUPConfirmPassword = (EditText) findViewById(R.id.UUPConfirmPassword);

        UUPasswordBtn = (Button) findViewById(R.id.UUPasswordBtn);

        UUPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatUserPassword();
            }
        });
    }

    private void updatUserPassword() {

        UserLoginData loginData = UserSharePrefManager.getInstance(UpdateUserPasswordActivity.this)
                .getUserDetail();

        String UmonNum = loginData.getMobile();

        String Upassword = UUPassword.getText().toString();
        String UCpassword = UUPConfirmPassword.getText().toString();

        if (!Upassword.equals(UCpassword)) {

            UUPConfirmPassword.setError("Incorrect Password");
            UUPConfirmPassword.requestFocus();
            return;
        }

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<UpdateUserPasswordResponse> call = ltgApi.updateUserPassword(UmonNum, UCpassword);
        call.enqueue(new Callback<UpdateUserPasswordResponse>() {
            @Override
            public void onResponse(Call<UpdateUserPasswordResponse> call, Response<UpdateUserPasswordResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    UpdateUserPasswordResponse passwordResponse = response.body();
                    String r = passwordResponse.getData().getMobile();

                    Toast.makeText(UpdateUserPasswordActivity.this, r, Toast.LENGTH_SHORT).show();
                    Toast.makeText(UpdateUserPasswordActivity.this, "Sucess+", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateUserPasswordActivity.this, "UnSucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserPasswordResponse> call, Throwable t) {

                Toast.makeText(UpdateUserPasswordActivity.this, "Try Again later", Toast.LENGTH_SHORT).show();
                Toast.makeText(UpdateUserPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
