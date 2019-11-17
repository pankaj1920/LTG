package com.svgptechnologies.ltg.Driver.DriverRegistration;

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

import com.svgptechnologies.ltg.Driver.DriverRegistration.VichelDocument.ServiceDocumentsActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverRegistration.DriverRegistrationResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverRigesterActivity extends AppCompatActivity {

    Button DriverRegisterBtn;

    EditText DR_Name, DR_mobile, DR_Email, DL_Password, DL_Confirm_Password;

    String ServiceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rigester);



        DriverRegisterBtn = (Button) findViewById(R.id.DriverRegisterBtn);

        DR_Name = (EditText) findViewById(R.id.DR_Name);
        DR_mobile = (EditText) findViewById(R.id.DR_mobile);
        DR_Email = (EditText) findViewById(R.id.DR_Email);
        DL_Password = (EditText) findViewById(R.id.DL_Password);
        DL_Confirm_Password = (EditText) findViewById(R.id.DL_Confirm_Password);


        Bundle bundle = getIntent ().getExtras ();
        ServiceName = bundle.getString ( "service_name" );


        DriverRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestReciveSms();
            }
        });


    }


    private void goToVerifyOTPactivity() {

        final String Dname = DR_Name.getText().toString();
        final String Dmobile = DR_mobile.getText().toString();
        final String Demail = DR_Email.getText().toString();
        final String Dpassword = DL_Password.getText().toString();
        final String DCpassword = DL_Confirm_Password.getText().toString();

        if (Dmobile.length() < 10 || Dmobile.length() > 10) {
            DR_mobile.setError("Enter The Correct Number");
            DR_mobile.requestFocus();
            return;
        }

        if (Dname.equals("")) {
            DR_Name.setError("Enter The Correct Name");
            DR_Name.requestFocus();
            return;
        }

        if (Demail.equals("")) {
            DR_Email.setError("Enter The Correct Email");
            DR_Email.requestFocus();
            return;
        }


        if (Dpassword.equals("")) {
            DL_Password.setError("Enter The Correct Password");
            DL_Password.requestFocus();
            return;
        }

        if (DCpassword.equals("")) {
            DL_Confirm_Password.setError("Incorrect Password");
            DL_Confirm_Password.requestFocus();
            return;
        }
        if (!Dpassword.equals(DCpassword)){

            DL_Confirm_Password.setError("Password not matched");
            DL_Confirm_Password.requestFocus();
            return;
        }

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<DriverRegistrationResponse> call = ltgApi.DriverRigestration(Dname, Dmobile, Demail, Dpassword);
        call.enqueue(new Callback<DriverRegistrationResponse>() {
            @Override
            public void onResponse(Call<DriverRegistrationResponse> call, Response<DriverRegistrationResponse> response) {


                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    DriverRegistrationResponse registrationResponse = response.body();
                    String DriverId = registrationResponse.getDriver_id();


                    Intent intent = new Intent(DriverRigesterActivity.this, ServiceDocumentsActivity.class);

                    //sending mobile number to Enter OTP Activity so that we can display that number their
                    Bundle bundle = new Bundle();
                    bundle.putString("DriverId", DriverId);
                    bundle.putString("Dmobile", Dmobile);
                    bundle.putString("serviceName", ServiceName);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    Toast.makeText(DriverRigesterActivity.this, "Sucess", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DriverRigesterActivity.this, DriverId, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DriverRigesterActivity.this, "Unsucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DriverRegistrationResponse> call, Throwable t) {
                Toast.makeText(DriverRigesterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverRigesterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean askForPermission(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(DriverRigesterActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        } else {
            return true;
        }
    }

    void displayPermission(String permission, Integer requestCode) {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(DriverRigesterActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(DriverRigesterActivity.this,
                    new String[]{permission}, requestCode);
        } else {

            ActivityCompat.requestPermissions(DriverRigesterActivity.this,
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
