package com.svgptechnologies.ltg.User.UpdateUserSetting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.UpdateUserName.UpdateUserNameResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginData;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.UserHomePageActivity;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserNameActivity extends AppCompatActivity {

    EditText UUName;
    Button UUNameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_name);


        UUName = (EditText) findViewById(R.id.UUName);
        UUNameBtn = (Button) findViewById(R.id.UUNameBtn);


        UUNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserName();
            }
        });
    }

    private void updateUserName() {

        UserLoginData userLoginData = UserSharePrefManager.getInstance(UpdateUserNameActivity.this)
                .getUserDetail();

        String Umob = userLoginData.getMobile();

        final String Uname = UUName.getText().toString();

        if (Uname.isEmpty()) {

            UUName.setError("Enter Name");
            UUName.requestFocus();
            return;
        }

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<UpdateUserNameResponse> call = ltgApi.updateUserName(Umob, Uname);
        call.enqueue(new Callback<UpdateUserNameResponse>() {
            @Override
            public void onResponse(Call<UpdateUserNameResponse> call, Response<UpdateUserNameResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    UpdateUserNameResponse nameResponse = response.body();
                    String n = nameResponse.getData().getMobile();
//
//                    SharedPreferences preferences = getSharedPreferences("MY_PREF",MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("userNavName", Uname);
//                    editor.commit();

                    Intent intent = new Intent(UpdateUserNameActivity.this, UserHomePageActivity.class);
                    //Create the bundle
                    Bundle bundle = new Bundle();
                    //Add your data from getFactualResults method to bundle
                    bundle.putString("userNavName", Uname);
                    intent.putExtras(bundle);
                    startActivity(intent);


                    Toast.makeText(UpdateUserNameActivity.this, n, Toast.LENGTH_SHORT).show();
                    Toast.makeText(UpdateUserNameActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(UpdateUserNameActivity.this, "Unsucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUserNameResponse> call, Throwable t) {

            }
        });

    }
}
