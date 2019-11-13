package com.svgptechnologies.ltg.Driver.DriverRegistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.svgptechnologies.ltg.Driver.DriverLoginActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverNamePassDialog.DriverIdPassDialogData;
import com.svgptechnologies.ltg.Json.DriverJson.DriverNamePassDialog.DriveridPassDialogResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverRegistrationSuccessfulActivity extends AppCompatActivity {

    Button DRScontinueBtn;
    ConstraintLayout mainConstrainLayout;
    TextView driverId, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration_successful);


        DRScontinueBtn = (Button) findViewById(R.id.DRScontinueBtn);

        mainConstrainLayout = (ConstraintLayout) findViewById(R.id.mainConstrainLayout);


        DRScontinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverRegistrationSuccessfulActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        showDriverIdPass();
        mainConstrainLayout.setVisibility(View.INVISIBLE);
    }

    private void showDriverIdPass() {

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("DriverId");

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<DriveridPassDialogResponse> call = ltgApi.getDriverIdPass(id);

        call.enqueue(new Callback<DriveridPassDialogResponse>() {
            @Override
            public void onResponse(Call<DriveridPassDialogResponse> call, Response<DriveridPassDialogResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    DriveridPassDialogResponse dialogResponse = response.body();

                    List<DriverIdPassDialogData> dialogData = dialogResponse.getData();

                    for (DriverIdPassDialogData data : dialogData) {

                        String Driverid = data.getUsername();
                        String password = data.getPassword();

                        showCustomDialog(Driverid, password);


                    }


                } else {
                    Toast.makeText(DriverRegistrationSuccessfulActivity.this, "Unsucessfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriveridPassDialogResponse> call, Throwable t) {

                Toast.makeText(DriverRegistrationSuccessfulActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverRegistrationSuccessfulActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Creating Custon Dialog FoR I have a referral code in Navigation Drawer
    private void showCustomDialog(String DriverId, String DriverPassword) {


        String driverID = DriverId;
        String driverPass = DriverPassword;


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View view = LayoutInflater.from(DriverRegistrationSuccessfulActivity.this).inflate(R.layout.activity_show_driver_user_name_pass_dialog_box, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(DriverRegistrationSuccessfulActivity.this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(view);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        //add this line to make your dialogbox radius round
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView Driverpassword = (TextView) view.findViewById(R.id.Driverpassword);
        TextView driverId = (TextView) view.findViewById(R.id.driverId);

        alertDialog.setCanceledOnTouchOutside(false);

        Driverpassword.setText(driverPass);
        driverId.setText(driverID);

        alertDialog.show();


        Button okBtn = view.findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                mainConstrainLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
