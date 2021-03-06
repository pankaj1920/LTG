package com.svgptechnologies.ltg.Driver.DriverRegistration.SelectService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.svgptechnologies.ltg.Driver.DriverRegistration.DriverRigesterActivity;
import com.svgptechnologies.ltg.Driver.SelectPaymentActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.SelectServiceType.SelectServiceTypeResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectServiceTypeActivity extends AppCompatActivity {

    RecyclerView serviceListRecyclerView;
    SelectServiceRecyclerAdapter adapter;
    String SerViceName;
    ShimmerFrameLayout selectServiceSimmer;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_select_service_type );


        selectServiceSimmer =  findViewById ( R.id.selectServiceSimmer );

        selectServiceSimmer.startShimmer ();

        serviceListRecyclerView = ( RecyclerView ) findViewById ( R.id.serviceListRecyclerView );
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager ( this, 2 );
        serviceListRecyclerView.setLayoutManager ( layoutManager );

        getSerViceList ( );
    }

    public void getSerViceList ( ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        Call<SelectServiceTypeResponse> call = ltgApi.getServiceList ( );

        call.enqueue ( new Callback<SelectServiceTypeResponse> ( ) {
            @Override
            public void onResponse ( Call<SelectServiceTypeResponse> call, Response<SelectServiceTypeResponse> response ) {

                final SelectServiceTypeResponse serviceTypeResponse = response.body ( );

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) && response.body ( ).getStatus ( ).equals ( "1" ) ) {

                    selectServiceSimmer.stopShimmer ();
                    selectServiceSimmer.setVisibility ( View.GONE );
                    serviceListRecyclerView.setVisibility ( View.VISIBLE );

                    adapter = new SelectServiceRecyclerAdapter ( SelectServiceTypeActivity.this, serviceTypeResponse.getData ( ) );
                    serviceListRecyclerView.setAdapter ( adapter );

                    adapter.OnItemClickListner ( new SelectServiceRecyclerAdapter.OnItemClickListner ( ) {
                        @Override
                        public void onSerViceTypeClicked ( View itemView, int Postion ) {

                            SerViceName = serviceTypeResponse.getData ( ).get ( Postion ).getServcie_name ( );

                            Toast.makeText ( SelectServiceTypeActivity.this, SerViceName, Toast.LENGTH_SHORT ).show ( );

                            Intent intent = new Intent ( SelectServiceTypeActivity.this, SelectPaymentActivity.class );

                            Bundle bundle = new Bundle ( );
                            bundle.putString ( "service_name", SerViceName );
                            intent.putExtras ( bundle );
                            startActivity ( intent );
                        }
                    } );


                } else {

                    Toast.makeText ( SelectServiceTypeActivity.this, "UnSucess", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<SelectServiceTypeResponse> call, Throwable t ) {

                Toast.makeText ( SelectServiceTypeActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( SelectServiceTypeActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );

            }
        } );
    }
}
