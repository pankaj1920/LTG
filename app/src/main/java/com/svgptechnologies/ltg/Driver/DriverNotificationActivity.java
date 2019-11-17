package com.svgptechnologies.ltg.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.BookingStatus.BookingStatusResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginData;
import com.svgptechnologies.ltg.Json.DriverJson.GetUserLocation.GetUserLocationData;
import com.svgptechnologies.ltg.Json.DriverJson.GetUserLocation.GetUserLocationResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;

import java.net.HttpURLConnection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverNotificationActivity extends AppCompatActivity {

    double userLat, userLang;
    String Umobile, driverId, Uname, Uaddress,getUserTripStatus;
    Button notiUserCancleBtn, notiUserAcceptBtn;
    CircleImageView notificationUserImage;
    TextView notiUserName, notiUsermobile, notiUserAddress;
    ImageView noNotificationImage;

    ConstraintLayout NotiConstLAyout;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_driver_notification );



        NotiConstLAyout = findViewById ( R.id.NotiConstLAyout );

        notificationUserImage = findViewById ( R.id.notificationUserImage );

        noNotificationImage = findViewById ( R.id.noNotificationImage );

        notiUserName = findViewById ( R.id.notiUserName );

        notiUsermobile = findViewById ( R.id.notiUsermobile );

        notiUserAddress = findViewById ( R.id.notiUserAddress );

        notiUserAcceptBtn = findViewById ( R.id.notiUserAcceptBtn );

        notiUserCancleBtn = findViewById ( R.id.notiUserCancleBtn );

        notiUserAcceptBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent = new Intent ( DriverNotificationActivity.this, DriverHomePageActivity.class );
                startActivity ( intent );
                finish ( );
            }
        } );

        notiUserCancleBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                cancleBooking ( );
            }
        } );
    }


    @Override
    protected void onStart ( ) {
        super.onStart ( );

        getUserLocation ( );
    }

    //fetch user location and show in Dialog box
    public void getUserLocation ( ) {

        DriverLoginData loginData = DriverSharePrefManager.getInstance ( DriverNotificationActivity.this ).getDriverDetail ( );
        String driverId = loginData.getDriver_id ( );
        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        Call<GetUserLocationResponse> call = ltgApi.getUserLocation ( driverId );

        call.enqueue ( new Callback<GetUserLocationResponse> ( ) {
            @Override
            public void onResponse ( Call<GetUserLocationResponse> call, Response<GetUserLocationResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {

                    GetUserLocationResponse userLocationResponse = response.body ( );

                    List<GetUserLocationData> userLocationData = userLocationResponse.getData ( );

                    // in on_start when this method will execute  if nobody has sent the request
                    // then data will be null then this if condition will not work
                    if ( userLocationData != null ) {


                        for (GetUserLocationData data : userLocationData) {

                            Uname = data.getUser_name ( );
                            Umobile = data.getUser_mobile ( );
                            Uaddress = data.getUser_address ( );
                            userLat = Double.parseDouble ( data.getUser_lat ( ) );
                            userLang = Double.parseDouble ( data.getUser_lang ( ) );
                            getUserTripStatus = data.getTrip_status ( );

                            Toast.makeText ( DriverNotificationActivity.this, "Trip Status" + getUserTripStatus, Toast.LENGTH_SHORT ).show ( );

                            if ( getUserTripStatus.equals ( "requested" ) ) {

                                NotiConstLAyout.setVisibility ( View.VISIBLE );
                                noNotificationImage.setVisibility ( View.GONE );
                            }


                        }


                    } else {
                        Toast.makeText ( DriverNotificationActivity.this, "user Data is Null", Toast.LENGTH_SHORT ).show ( );
                    }

                } else {

                    Toast.makeText ( DriverNotificationActivity.this, "Gert User Location Unsucess", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<GetUserLocationResponse> call, Throwable t ) {

                Toast.makeText ( DriverNotificationActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
            }
        } );

    }

    private void cancleBooking ( ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
        Call<BookingStatusResponse> call = ltgApi.cancleBooking ( driverId, "cancelled" );
        call.enqueue ( new Callback<BookingStatusResponse> ( ) {
            @Override
            public void onResponse ( Call<BookingStatusResponse> call, Response<BookingStatusResponse> response ) {

                BookingStatusResponse bookingResponse = response.body ( );
                String status = bookingResponse.getData ( ).getStatus ( );

                Toast.makeText ( DriverNotificationActivity.this, "Status : " + status, Toast.LENGTH_SHORT ).show ( );

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) && bookingResponse.getData ( ).getStatus ( ).equals ( "1" ) ) {

                    Snackbar.make ( findViewById ( android.R.id.content ), "Your Trip is Cancelled", Snackbar.LENGTH_LONG )
                            .setAction ( "OK", null )
                            .setDuration ( 5000 )
                            .setActionTextColor ( Color.WHITE ).show ( );

                    // call the method
                    getUserLocation ( );

                } else {

                    Toast.makeText ( DriverNotificationActivity.this, "cancle Booking UnSucessful", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<BookingStatusResponse> call, Throwable t ) {

                Toast.makeText ( DriverNotificationActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverNotificationActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }

}
