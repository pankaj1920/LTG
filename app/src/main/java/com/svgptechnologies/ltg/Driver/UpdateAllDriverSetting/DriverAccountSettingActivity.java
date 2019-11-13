package com.svgptechnologies.ltg.Driver.UpdateAllDriverSetting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginData;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail.UpdateDriverAllDetailResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAllDetail.UpdateDriverAllSettingImage.UpdateDriverAllSettingImageResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;

import java.io.File;
import java.net.HttpURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAccountSettingActivity extends AppCompatActivity {

    Button DriverAccountUpdateBtn;
    ImageButton DriverImagaePicker;
    CircleImageView driver_account_logo;
    EditText DUNameEditText, DUNumberEditText, DUEmailEditText, DUAddressEditText, DUPasswordEditText;
    String photoPath;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account_setting);



        DUNameEditText = (EditText) findViewById(R.id.DUNameEditText);

        DUNumberEditText = (EditText) findViewById(R.id.DUNumberEditText);

        DriverImagaePicker = (ImageButton) findViewById(R.id.DriverImagaePicker);

        driver_account_logo = (CircleImageView) findViewById(R.id.driver_account_logo);

        DUEmailEditText = (EditText) findViewById(R.id.DUEmailEditText);

        DUAddressEditText = (EditText) findViewById(R.id.DUAddressEditText);

        DUPasswordEditText = (EditText) findViewById(R.id.DUPasswordEditText);

        DriverAccountUpdateBtn = (Button) findViewById(R.id.DriverAccountUpdateBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);


        DriverImagaePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Permission for to access Gallery
                requestMediaFilePermission();
            }
        });


        DriverAccountUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDriverImage();

            }
        });


    }


    private void updateDriverSetting() {

        progressDialog.show();

        DriverLoginData loginData = DriverSharePrefManager.getInstance(DriverAccountSettingActivity.this).getDriverDetail();

        String driver_Id = loginData.getDriver_id();

        String name = DUNameEditText.getText().toString();

        String dmobile = DUNumberEditText.getText().toString();

        String email = DUEmailEditText.getText().toString().trim();

        String address = DUAddressEditText.getText().toString();

        String password = DUPasswordEditText.getText().toString();

        Toast.makeText(this, driver_Id, Toast.LENGTH_SHORT).show();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<UpdateDriverAllDetailResponse> call = ltgApi.updateDriverAllDetail(driver_Id, name, email, password, dmobile, address);

        call.enqueue(new Callback<UpdateDriverAllDetailResponse>() {
            @Override
            public void onResponse(Call<UpdateDriverAllDetailResponse> call, Response<UpdateDriverAllDetailResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    UpdateDriverAllDetailResponse detailResponse = response.body();
                    String status = detailResponse.getData().getStatus();
                    String otp = detailResponse.getData().getOtp();
                    Toast.makeText(DriverAccountSettingActivity.this, "Otp : " + otp, Toast.LENGTH_SHORT).show();

                    if (status.equals("1") && !otp.isEmpty()) {
                        Toast.makeText(DriverAccountSettingActivity.this, "Document Uploade", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DriverAccountSettingActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(DriverAccountSettingActivity.this, "Unsucess", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateDriverAllDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DriverAccountSettingActivity.this, "Try Agail Later", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(DriverAccountSettingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateDriverImage() {

        progressDialog.show();

        MultipartBody.Part profilePic = null;
        if (photoPath != null) {

            File file = new File(photoPath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // this "image" is String in UserImageData
            profilePic = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        }
        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        DriverLoginData loginData = DriverSharePrefManager.getInstance(DriverAccountSettingActivity.this).getDriverDetail();
        String driver_Id = loginData.getDriver_id();

        Call<UpdateDriverAllSettingImageResponse> call = ltgApi.uploadDriveAllSettingImage(driver_Id, profilePic);

        call.enqueue(new Callback<UpdateDriverAllSettingImageResponse>() {
            @Override
            public void onResponse(Call<UpdateDriverAllSettingImageResponse> call, Response<UpdateDriverAllSettingImageResponse> response) {

                UpdateDriverAllSettingImageResponse uploadResponse = response.body();
                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {


                    UpdateDriverAllSettingImageResponse allSettingImageResponse = response.body();

                    String status = allSettingImageResponse.getData().getStatus();

                    Toast.makeText(DriverAccountSettingActivity.this, "Status : "+ status, Toast.LENGTH_SHORT).show();

                    if (status.equals("2")){

                        Toast.makeText(DriverAccountSettingActivity.this, uploadResponse.getData().getDriver_id(), Toast.LENGTH_SHORT).show();

                        String imageUrl = allSettingImageResponse.getData().getDriver_image();

                        Toast.makeText(DriverAccountSettingActivity.this, "Image : "+imageUrl, Toast.LENGTH_SHORT).show();

                        updateDriverSetting();

                        if (!imageUrl.isEmpty()){
                            Intent intent = new Intent(DriverAccountSettingActivity.this, DriverUpdateAllSettingOTP.class);
                            startActivity(intent);
                            // finish();


                        }

                        Toast.makeText(DriverAccountSettingActivity.this, "Sucessfully Uploaded", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(DriverAccountSettingActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(DriverAccountSettingActivity.this, "UnSucess", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UpdateDriverAllSettingImageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DriverAccountSettingActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                Toast.makeText(DriverAccountSettingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }




    private void showFileChooser() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                photoPath = cursor.getString(columnIndex);
                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                driver_account_logo.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                cursor.close();

            }
            // When an Video is picked
            else {
                progressDialog.dismiss();
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }


    }





    //Runtime Permission so that it can AutoVerify The Otp in EnterOtpActivity
    private void requestMediaFilePermission() {
        if (askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1)) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            displayPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
        }
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(DriverAccountSettingActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {

            showFileChooser();
            //  Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    void displayPermission(String permission, Integer requestCode) {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                DriverAccountSettingActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(DriverAccountSettingActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(DriverAccountSettingActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 1) {
                requestMediaFilePermission();
            }

        }
        // this else statment will run wen user click on dont ask again and deined the permission and still click on allow button
        else {
            Toast.makeText(this, "Enable Message permission from Setting", Toast.LENGTH_SHORT).show();
        }
    }



}
