package com.svgptechnologies.ltg.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.GetUserDetail.GetUserDetailData;
import com.svgptechnologies.ltg.Json.UserJson.GetUserDetail.GetUserDetailResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginData;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.UpdateUserSetting.UpdateUserEmailActivity;
import com.svgptechnologies.ltg.User.UpdateUserSetting.UpdateUserNameActivity;
import com.svgptechnologies.ltg.User.UpdateUserSetting.UpdateUserPasswordActivity;
import com.svgptechnologies.ltg.User.UpdateUserSetting.UserUploadImageActivity;

import java.net.HttpURLConnection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAccountSettingActivity extends AppCompatActivity {

    CircleImageView userImage;
    ImageButton UseriCameraPick;
    ConstraintLayout account_image_layout;
    TextView UUNameTextView, UUNumberTextView, UUEmailTextView, UUPasswordTextView;
    Button signout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_setting);
        UUNumberTextView = (TextView) findViewById(R.id.UUNumberTextView);

        UUNameTextView = (TextView) findViewById(R.id.UUNameTextView);

        UUEmailTextView = (TextView) findViewById(R.id.UUEmailTextView);

        UseriCameraPick = (ImageButton) findViewById(R.id.UseriCameraPick);

        UUPasswordTextView = (TextView) findViewById(R.id.UUPasswordTextView);

        userImage = (CircleImageView) findViewById(R.id.userImage);

        account_image_layout = (ConstraintLayout) findViewById(R.id.account_image_layout);

        signout_btn = (Button) findViewById(R.id.signout_btn);

        getUserDetail();

        UUNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserAccountSettingActivity.this, UpdateUserNameActivity.class);
                startActivity(intent);
            }
        });


        UUEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserAccountSettingActivity.this, UpdateUserEmailActivity.class);
                startActivity(intent);
            }
        });

        UUPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserAccountSettingActivity.this, UpdateUserPasswordActivity.class);
                startActivity(intent);
            }
        });


        UUNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserAccountSettingActivity.this, UpdateUserNameActivity.class);
                startActivity(intent);
            }
        });


        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signout();
            }
        });


        account_image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccountSettingActivity.this, UserUploadImageActivity.class);
                startActivity(intent);
            }
        });

        UseriCameraPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserAccountSettingActivity.this, UserUploadImageActivity.class);
                startActivity(intent);
            }
        });
    }


    public void Signout() {
        //we are callin Logout Method from SharePrefManager will will delet all user detail from share prefrences
        UserSharePrefManager.getInstance(UserAccountSettingActivity.this).UserLogout();
        DriverSharePrefManager.getInstance(UserAccountSettingActivity.this).DriverLogout();

        Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserAccountSettingActivity.this, UserLoginActivity.class);
        startActivity(intent);
    }


    public void getUserDetail() {


        final UserLoginData data = UserSharePrefManager.getInstance(UserAccountSettingActivity.this).getUserDetail();

        String mobNum = data.getMobile();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<GetUserDetailResponse> call = ltgApi.getUserDetail(mobNum);

        call.enqueue(new Callback<GetUserDetailResponse>() {
            @Override
            public void onResponse(Call<GetUserDetailResponse> call, Response<GetUserDetailResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    GetUserDetailResponse detailResponse = response.body();

                    List<GetUserDetailData> getUserDetailData = detailResponse.getData();

                    for (GetUserDetailData detailData : getUserDetailData) {

                        String name = detailData.getName();
                        String number = detailData.getMobile();
                        String email = detailData.getEmail();
                        String image = detailData.getImage();

                        UUNameTextView.setText(name);

                        UUNumberTextView.setText(number);
                        UUEmailTextView.setText(email);

                        if (detailData.getImage().isEmpty()) {

                            userImage.setImageResource(R.drawable.ic_upload_img);

                        } else {

                            Picasso.with(UserAccountSettingActivity.this).load(image).into(userImage);
                        }

                    }


                } else {

                    Toast.makeText(UserAccountSettingActivity.this, "UnSucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserDetailResponse> call, Throwable t) {

                Toast.makeText(UserAccountSettingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(UserAccountSettingActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
