package com.svgptechnologies.ltg.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.svgptechnologies.ltg.AllowLocationActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginResponse;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.UserForgotPassword.UserForgetPasswordActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    Button UELoginBtn;
    TextView forgotPassword, RegisterTextView;
    String UserToken;
    EditText IdEditText, PasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        UELoginBtn = (Button) findViewById(R.id.UELoginBtn);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword);

        RegisterTextView = (TextView) findViewById(R.id.RegisterTextView);

        IdEditText = (EditText) findViewById(R.id.IdEditText);

        PasswordEditText = (EditText) findViewById(R.id.PasswordEditText);


//        // Getting Firebase Token
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//
//                UserToken = instanceIdResult.getToken();
//            }
//        });
//

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserLoginActivity.this, UserForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        RegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intent);
            }
        });

        UELoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserLogin();


            }
        });


    }

    private void UserLogin() {

        String username = IdEditText.getText().toString();
        String password = PasswordEditText.getText().toString();


        if (username.equals("")) {

            IdEditText.setError("Invalid Email or Number");
            IdEditText.requestFocus();
            return;

        }
        if (password.equals("")) {

            PasswordEditText.setError("Invalid Password");
            PasswordEditText.requestFocus();
            return;

        }

       // Toast.makeText(this, UserToken, Toast.LENGTH_SHORT).show();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<UserLoginResponse> call = ltgApi.UserLogin(username, password, "Enter Token");
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                UserLoginResponse loginResponse = response.body();

                if (response.isSuccessful() && loginResponse.getStatus().equals("1")) {

                    //if the login Responwe is sucessfull we will save the user
                    UserSharePrefManager.getInstance(UserLoginActivity.this).saveUserLoginDetail(loginResponse);

                    Toast.makeText(UserLoginActivity.this, loginResponse.getStatus(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserLoginActivity.this, AllowLocationActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(UserLoginActivity.this, "unSucessfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Toast.makeText(UserLoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
