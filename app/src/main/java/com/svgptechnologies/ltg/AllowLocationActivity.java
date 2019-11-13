package com.svgptechnologies.ltg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.UserHomePageActivity;
import com.svgptechnologies.ltg.User.UserLoginActivity;

public class AllowLocationActivity extends AppCompatActivity {

    ConstraintLayout allowLocationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_location);

        allowLocationLayout = (ConstraintLayout) findViewById(R.id.allowLocationLayout);

        allowLocationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestLocationPermission();
            }
        });
    }


    //here in On start it will check wheather permission is already givien if given then it will directly go to next activity this activy will no open
    @Override
    protected void onStart() {
        super.onStart();

        if (!UserSharePrefManager.getInstance(this).UserAlreadyLoggedIn()) {
            Intent intent = new Intent(AllowLocationActivity.this, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        //here if permission is already their then it will drictely go to HomPage Activity
        if (askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1)) {
            Intent intent = new Intent(AllowLocationActivity.this, UserHomePageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

            //if user is already save then directly go to home activity
        } else if (!UserSharePrefManager.getInstance(this).UserAlreadyLoggedIn()) {
            Intent intent = new Intent(AllowLocationActivity.this, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            return;
        }

    }


    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(AllowLocationActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void displayPermission(String permission, Integer requestCode) {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                AllowLocationActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(AllowLocationActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(AllowLocationActivity.this,
                    new String[]{permission}, requestCode);
        }
    }


    private void requestLocationPermission() {
        if (askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1)) {
            Intent intent = new Intent(AllowLocationActivity.this, UserHomePageActivity.class);
            startActivity(intent);
        } else {
            displayPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 1) {
                requestLocationPermission();
            }

        }
        // this else statment will run wen user click on dont ask again and deined the permission and still click on allow button
        else {
            Toast.makeText(this, "Enable Location permission from Setting", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}

