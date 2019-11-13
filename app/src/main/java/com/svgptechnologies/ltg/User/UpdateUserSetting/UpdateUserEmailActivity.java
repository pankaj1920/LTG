package com.svgptechnologies.ltg.User.UpdateUserSetting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.UpdateUserEmail.UpdateUserEmailResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginData;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserEmailActivity extends AppCompatActivity {

    EditText UUEmail;
    Button UUEmailBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_email);


        UUEmail = (EditText) findViewById(R.id.UUEmail);

        UUEmailBtn = (Button) findViewById(R.id.UUEmailBtn);

        UUEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userUpdateEmail();
            }
        });
    }

    private void userUpdateEmail() {

        UserLoginData loginData = UserSharePrefManager.getInstance(UpdateUserEmailActivity.this)
                .getUserDetail();

        String mobNum = loginData.getMobile();

        String email = UUEmail.getText().toString();

        if (email.isEmpty()) {

            UUEmail.setError("Emter Email");
            UUEmail.requestFocus();
            return;
        }

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<UpdateUserEmailResponse> call = ltgApi.updateUserEmail(mobNum, email);

        call.enqueue(new Callback<UpdateUserEmailResponse>() {
            @Override
            public void onResponse(Call<UpdateUserEmailResponse> call, Response<UpdateUserEmailResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {


                    UpdateUserEmailResponse emailResponse = response.body();
                    String m = emailResponse.getData().getMobile();

                    Toast.makeText(UpdateUserEmailActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    Toast.makeText(UpdateUserEmailActivity.this, m, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(UpdateUserEmailActivity.this, "UnSucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserEmailResponse> call, Throwable t) {

                Toast.makeText(UpdateUserEmailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UpdateUserEmailActivity.this, "Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
