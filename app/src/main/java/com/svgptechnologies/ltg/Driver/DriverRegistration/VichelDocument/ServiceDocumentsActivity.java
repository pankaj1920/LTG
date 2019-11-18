package com.svgptechnologies.ltg.Driver.DriverRegistration.VichelDocument;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.svgptechnologies.ltg.Driver.DriverRegistration.DriverOtpActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.VichelDocument.DocumentListResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.User.SeachDriver.SearchDriverRecyclerAdapter;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDocumentsActivity extends AppCompatActivity {

    RecyclerView serviceDocumentRecyclerView;
    ServiceDocumentRecyclerAdapter adapter;
    Button uploadVechileDocumentBtn;
    ConstraintLayout serviceDocumentLayout;
    String Driverid, DriverMobile, ServiceName;
    ShimmerFrameLayout selectDocummentSimmer;
    String photoPath;
    ProgressDialog progressDialog;
    ImageView DocumentImage;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_service_documents );

        serviceDocumentRecyclerView = findViewById ( R.id.serviceDocumentRecyclerView );
        serviceDocumentRecyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

        uploadVechileDocumentBtn = findViewById ( R.id.uploadVechileDocumentBtn );

        selectDocummentSimmer = findViewById ( R.id.selectDocummentSimmer );

        serviceDocumentLayout = findViewById ( R.id.serviceDocumentLayout );


        selectDocummentSimmer.startShimmer ( );

        serviceDocumentLayout.setVisibility ( View.GONE );

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);

//        //Getting data fromDriverRegisterActivity
//        Bundle bundle = getIntent ( ).getExtras ( );
//        Driverid = bundle.getString ( "DriverId" );
//        DriverMobile = bundle.getString ( "Dmobile" );
//        ServiceName = bundle.getString ( "serviceName" );

        GetServiceDocument ( );

        uploadVechileDocumentBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                Intent intent = new Intent ( ServiceDocumentsActivity.this, DriverOtpActivity.class );

                Bundle bundle1 = new Bundle ( );
                bundle1.putString ( "DriverId", Driverid );
                bundle1.putString ( "Dmobile", DriverMobile );
                intent.putExtras ( bundle1 );

                startActivity ( intent );
            }
        } );

    }

    public void GetServiceDocument ( ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        Call<DocumentListResponse> call = ltgApi.getServiceDocument ( "car" );

        call.enqueue ( new Callback<DocumentListResponse> ( ) {
            @Override
            public void onResponse ( Call<DocumentListResponse> call, Response<DocumentListResponse> response ) {

                final DocumentListResponse documentListResponse = response.body ( );
                String status = documentListResponse.getStatus ( );

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) && status.equals ( "1" ) ) {

                    Toast.makeText ( ServiceDocumentsActivity.this, "Sucess", Toast.LENGTH_SHORT ).show ( );

                    String data = documentListResponse.getData ( ).toString ( );

                    selectDocummentSimmer.stopShimmer ( );
                    selectDocummentSimmer.setVisibility ( View.GONE );
                    serviceDocumentLayout.setVisibility ( View.VISIBLE );

                    adapter = new ServiceDocumentRecyclerAdapter ( ServiceDocumentsActivity.this, documentListResponse.getData ( ) );
                    serviceDocumentRecyclerView.setAdapter ( adapter );

                    adapter.setOnItemClickListner ( new ServiceDocumentRecyclerAdapter.OnItemClickListner ( ) {
                        @Override
                        public void onPickDocumentClickListner ( View view, int position ) {

                            String doc_type = documentListResponse.getData ( ).get ( position ).getDocument_type ( );
                            Toast.makeText ( ServiceDocumentsActivity.this, "Doc : " + doc_type, Toast.LENGTH_SHORT ).show ( );

                            requestMediaFilePermission ();

                            DocumentImage = view.findViewById ( R.id.DocumentImage );


                        }
                    } );


                } else {

                    serviceDocumentLayout.setVisibility ( View.GONE );
                    Toast.makeText ( ServiceDocumentsActivity.this, "UnSucessful", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<DocumentListResponse> call, Throwable t ) {

                serviceDocumentLayout.setVisibility ( View.GONE );

                Toast.makeText ( ServiceDocumentsActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( ServiceDocumentsActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
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


                String[] filePathColumn = { MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                photoPath = cursor.getString(columnIndex);
                //str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                DocumentImage.setImageBitmap( BitmapFactory.decodeFile(photoPath));
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
        if (askForPermission( Manifest.permission.READ_EXTERNAL_STORAGE, 1)) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            displayPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
        }
    }

    private boolean askForPermission(String permission, Integer requestCode) {
        if ( ContextCompat.checkSelfPermission(ServiceDocumentsActivity.this, permission)
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
                ServiceDocumentsActivity.this, permission)) {

            //This is called if user has denied the permission before
            //In this case I am just asking the permission again
            ActivityCompat.requestPermissions(ServiceDocumentsActivity.this,
                    new String[]{permission}, requestCode);

        } else {

            ActivityCompat.requestPermissions(ServiceDocumentsActivity.this,
                    new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ( ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

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
