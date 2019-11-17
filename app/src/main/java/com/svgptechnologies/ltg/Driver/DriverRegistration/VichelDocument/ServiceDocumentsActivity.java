package com.svgptechnologies.ltg.Driver.DriverRegistration.VichelDocument;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_service_documents );

        serviceDocumentRecyclerView = findViewById ( R.id.serviceDocumentRecyclerView );
        serviceDocumentRecyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

        uploadVechileDocumentBtn = findViewById ( R.id.uploadVechileDocumentBtn );

        serviceDocumentLayout = findViewById ( R.id.serviceDocumentLayout );

        serviceDocumentLayout.setVisibility ( View.GONE );

        //Getting data fromDriverRegisterActivity
        Bundle bundle = getIntent ( ).getExtras ( );
        Driverid = bundle.getString ( "DriverId" );
        DriverMobile = bundle.getString ( "Dmobile" );
        ServiceName = bundle.getString ( "serviceName" );

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

        Call<DocumentListResponse> call = ltgApi.getServiceDocument ( ServiceName );

        call.enqueue ( new Callback<DocumentListResponse> ( ) {
            @Override
            public void onResponse ( Call<DocumentListResponse> call, Response<DocumentListResponse> response ) {

                DocumentListResponse documentListResponse = response.body ( );
                String status = documentListResponse.getStatus ( );

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) && status.equals ( "1" ) ) {

                    Toast.makeText ( ServiceDocumentsActivity.this, "Sucess", Toast.LENGTH_SHORT ).show ( );

                    String data = documentListResponse.getData ( ).toString ( );

                    adapter = new ServiceDocumentRecyclerAdapter ( ServiceDocumentsActivity.this, documentListResponse.getData ( ) );
                    serviceDocumentRecyclerView.setAdapter ( adapter );

                    serviceDocumentLayout.setVisibility ( View.VISIBLE );

             /*       serviceDocumentRecyclerView.addOnScrollListener ( new RecyclerView.OnScrollListener ( ) {
                        @Override
                        public void onScrollStateChanged ( @NonNull RecyclerView recyclerView, int newState ) {
                            super.onScrollStateChanged ( recyclerView, newState );

                            if ( ! serviceDocumentRecyclerView.canScrollVertically ( 1 ) ) {

                           //     Toast.makeText ( ServiceDocumentsActivity.this, "Reached At Last", Toast.LENGTH_SHORT ).show ( );
                                uploadVechileDocument.setVisibility ( View.VISIBLE );
                            }else {

                                uploadVechileDocument.setVisibility ( View.GONE );
                            }
                        }
                    } );
*/

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
}
