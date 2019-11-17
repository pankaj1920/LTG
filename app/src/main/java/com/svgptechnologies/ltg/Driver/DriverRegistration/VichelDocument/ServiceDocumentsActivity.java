package com.svgptechnologies.ltg.Driver.DriverRegistration.VichelDocument;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_service_documents );

        serviceDocumentRecyclerView = findViewById ( R.id.serviceDocumentRecyclerView );
        serviceDocumentRecyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

        uploadVechileDocumentBtn = findViewById ( R.id.uploadVechileDocumentBtn );

        serviceDocumentLayout = findViewById ( R.id.serviceDocumentLayout );

        serviceDocumentLayout.setVisibility ( View.GONE );

        GetServiceDocument ( );

    }

    public void GetServiceDocument ( ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        Call<DocumentListResponse> call = ltgApi.getServiceDocument ( "bike" );

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
