package com.svgptechnologies.ltg.Driver.DriverTripHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginData;
import com.svgptechnologies.ltg.Json.DriverJson.DriverTripHistory.DriverTripHistoryResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverTipHistoryActivity extends AppCompatActivity {

    RecyclerView driverTripHistoryRecycler;
    DriverTripHistoryRecyclerAdapter adapter;
    TextView driverNoHistoryTxt;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_driver_tip_history );

        driverTripHistoryRecycler = findViewById ( R.id.driverTripHistoryRecycler );
        driverTripHistoryRecycler.setLayoutManager ( new LinearLayoutManager ( this ) );

        driverNoHistoryTxt = findViewById ( R.id.driverNoHistoryTxt );

        GetDriverHistory ( );
    }

    private void GetDriverHistory ( ) {

        DriverLoginData loginData = DriverSharePrefManager.getInstance ( DriverTipHistoryActivity.this ).getDriverDetail ( );
        String driverId = loginData.getDriver_id ( );

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
        Call<DriverTripHistoryResponse> call = ltgApi.getDriverTripHistory ( driverId );

        call.enqueue ( new Callback<DriverTripHistoryResponse> ( ) {
            @Override
            public void onResponse ( Call<DriverTripHistoryResponse> call, Response<DriverTripHistoryResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {

                    DriverTripHistoryResponse tripHistoryResponse = response.body ( );

                    driverNoHistoryTxt.setVisibility ( View.GONE );
                    driverTripHistoryRecycler.setVisibility ( View.VISIBLE );

                    adapter = new DriverTripHistoryRecyclerAdapter ( tripHistoryResponse.getData ( ), DriverTipHistoryActivity.this );
                    driverTripHistoryRecycler.setAdapter ( adapter );
                } else {
                    Toast.makeText ( DriverTipHistoryActivity.this, "unSucess", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<DriverTripHistoryResponse> call, Throwable t ) {

                Toast.makeText ( DriverTipHistoryActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverTipHistoryActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


}
