package com.svgptechnologies.ltg.User.UserForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UserForgetPasswordActivity extends AppCompatActivity {

    Button UF_GetOtpBtn;
    EditText UF_EnterNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forget_password);


        UF_EnterNumber = (EditText) findViewById(R.id.UF_EnterNumber);

        UF_GetOtpBtn = (Button) findViewById(R.id.UF_GetOtpBtn);

        UF_GetOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userForgotPasswordOtp();
            }
        });

    }

    private void userForgotPasswordOtp() {

        final String mobleNum = UF_EnterNumber.getText().toString();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<ResponseBody> call = ltgApi.userForgotPassOtp(mobleNum);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    Toast.makeText(UserForgetPasswordActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserForgetPasswordActivity.this, UserChangePasswordActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("UF_MobNum",mobleNum);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    Toast.makeText(UserForgetPasswordActivity.this, "Unsucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(UserForgetPasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserForgetPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
