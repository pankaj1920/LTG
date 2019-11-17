package com.svgptechnologies.ltg.Driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.svgptechnologies.ltg.CustomerCareActivity;
import com.svgptechnologies.ltg.Driver.UpdateAllDriverSetting.DriverAccountSettingActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.BookingStatus.BookingStatusResponse;
import com.svgptechnologies.ltg.Json.DriverJson.DriverLogin.DriverLoginData;
import com.svgptechnologies.ltg.Json.DriverJson.GetDriverDetails.GetDriverDetailData;
import com.svgptechnologies.ltg.Json.DriverJson.GetDriverDetails.GetDriverDetailsResponse;
import com.svgptechnologies.ltg.Json.DriverJson.GetUserLocation.GetUserLocationData;
import com.svgptechnologies.ltg.Json.DriverJson.GetUserLocation.GetUserLocationResponse;
import com.svgptechnologies.ltg.Json.DriverJson.PostDriverCurrentLocation.PostDriverCurrentLocationResponse;
import com.svgptechnologies.ltg.Json.DriverJson.SendDriverLocation.SendDriverLocationResponse;
import com.svgptechnologies.ltg.Json.DriverJson.UpdateDriverAvilability.UpdateDriverAviabilityResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.ShareAppActivity;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;
import com.svgptechnologies.ltg.User.AboutUsActivity;
import com.svgptechnologies.ltg.User.UserHomePageActivity;
import com.svgptechnologies.ltg.User.UserTermsConditionActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverHomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
        , GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker, mDropLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private DrawerLayout driver_drawer_layout;
    public static TextView driverCurrentLocation, driverPickUpLocation;
    PlacesClient placesClient;
    private UiSettings mUiSettings;
    boolean isValid;
    boolean isAcceptBooking = false;
    ConstraintLayout DriverButtonLayout;
    ImageView DriverDropMarker, DriverPickupMarker;
    View mapView;
    ArrayList markerPoints = new ArrayList ( );
    LatLng pickup, CurrentLatLng;
    Button DriverCompletBtn, DriverCancleTrip;
    TextView DNname, DNnumber;
    CircleImageView DNaccount_image;
    Switch AvilabiltyBtn;
    String address, postalCode;
    double latitude, longitude;
    LatLng stFrancis = new LatLng ( 29.0780, 80.1036 );
    double userLat, userLang;
    String Umobile, driverId, Uname, Uaddress;
    String getUserTripStatus;
    private volatile boolean stopthread = false;
    volatile boolean DriverIsOnline = true;
    volatile boolean stopUserDetailthread = true;
    volatile boolean openGetUserDialogBox = true;

    //    Get Updated Current Location related api
    private static final long UPDATE_IN_MILL = 10000;
    private static final long FAST_IN_MILL = 10000;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    public SettingsClient msettingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentlocation;

    double sendDriverLatitude, sendDriverLongitude;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_driver_home_page );


        Toolbar driverHomeToolbar = findViewById ( R.id.driverHomeToolbar );
        driverHomeToolbar.setTitle ( R.string.letGo );
        setSupportActionBar ( driverHomeToolbar );

        driverCurrentLocation = findViewById ( R.id.driverCurrentLocation );

        driverPickUpLocation = findViewById ( R.id.driverPickUpLocation );

        DriverPickupMarker = findViewById ( R.id.DriverPickupMarker );

        DriverCompletBtn = findViewById ( R.id.DriverCompletBtn );

        DriverCancleTrip = findViewById ( R.id.DriverCancleTrip );

        AvilabiltyBtn = findViewById ( R.id.AvilabiltyBtn );

        DriverButtonLayout = findViewById ( R.id.DriverButtonLayout );

        //mean whenever user will drag the map by default address will change in pickupLocation EditText
        isValid = true;


        DrawerLayout driver_drawer_layout = findViewById ( R.id.driver_drawer_layout );

        //to handel the click event of navigation view we need refrence of navigation view

        NavigationView navigationView = findViewById ( R.id.driver_nav_view );
        navigationView.setNavigationItemSelectedListener ( this );

        //to get drawerIcon to open Nav Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( DriverHomePageActivity.this,
                driver_drawer_layout, driverHomeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );

        //to change the toggle buttom on nav drawer
        toggle.getDrawerArrowDrawable ( ).setColor ( getResources ( ).getColor ( R.color.white ) );

        driver_drawer_layout.addDrawerListener ( toggle );
        toggle.syncState ( );


        // getting nav header so tthat we can change detail of navHeader
        View headerView = navigationView.getHeaderView ( 0 );
        DNname = headerView.findViewById ( R.id.DNname );
        DNnumber = headerView.findViewById ( R.id.DNnumber );
        DNaccount_image = headerView.findViewById ( R.id.DNaccount_image );


        //getting Driver Id
        DriverLoginData loginData = DriverSharePrefManager.getInstance ( DriverHomePageActivity.this ).getDriverDetail ( );
        driverId = loginData.getDriver_id ( );


        DNname.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                Intent intent = new Intent ( DriverHomePageActivity.this, DriverAccountSettingActivity.class );
                startActivity ( intent );
            }
        } );

        DNaccount_image.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                Intent intent = new Intent ( DriverHomePageActivity.this, DriverAccountSettingActivity.class );
                startActivity ( intent );
            }
        } );


        DNnumber.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                Intent intent = new Intent ( DriverHomePageActivity.this, DriverAccountSettingActivity.class );
                startActivity ( intent );
            }
        } );


        setDriverNavDetail ( );
        getDNavNameFromUpdateName ( );


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( )
                .findFragmentById ( R.id.Drivermap );
        mapFragment.getMapAsync ( this );

        driverCurrentLocation.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {

//                DriverDropMarker.setVisibility(View.GONE);
                DriverPickupMarker.setVisibility ( View.VISIBLE );
                isValid = true;
            }
        } );


        DriverCompletBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {

                BookingCompleted ( );

            }
        } );

        // here we are calling this method to initalize the FusedLocation Apis to get Updated Current Location
        intalizeFusedLocation ( );
        // when app will open this method will be called which will store current Latitude Longitude
        getCurrentLocation ( );
        PostDriverCurrentLocation ( );


        AvilabiltyBtn.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged ( CompoundButton buttonView, boolean isChecked ) {

                if ( isChecked ) {

                    DriverIsOnline = true;

                    //Saving the State of Switch
                    SharedPreferences.Editor editor = getSharedPreferences ( "switch_pref", MODE_PRIVATE ).edit ( );
                    editor.putBoolean ( "online", true );
                    editor.apply ( );

                    //make Driver Avilable and Online
                    makeDriverOnline ( );

                    // when it Avilabilty button is clicke this method is clicked and it will update the current location
                    getCurrentLocation ( );


                } else {

                    DriverIsOnline = false;

                    //when Driver is offline we have to stop send driver data to user
                    stopUpdateLocationThread ( );

                    //Saving the State of Switch
                    SharedPreferences.Editor editor = getSharedPreferences ( "switch_pref", MODE_PRIVATE ).edit ( );
                    editor.putBoolean ( "online", false );
                    editor.apply ( );

                    //make Driver UnAvilabel and Offline
                    makeDriverOffline ( );

                }
            }
        } );

        // fetching the saved state of switch and setting in switch
        SharedPreferences sharedPrefs = getSharedPreferences ( "switch_pref", MODE_PRIVATE );
        AvilabiltyBtn.setChecked ( sharedPrefs.getBoolean ( "online", true ) );


        driverPickUpLocation.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                autoCompleteLocation ( );
            }
        } );

        //to set My Location Button at Botton
        mapView = mapFragment.getView ( );


    }


    private void setDriverNavDetail ( ) {

        DriverLoginData loginData = DriverSharePrefManager.getInstance ( DriverHomePageActivity.this ).getDriverDetail ( );
        String DriverId = loginData.getDriver_id ( );

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
        Call<GetDriverDetailsResponse> call = ltgApi.getDriverDetail ( DriverId );
        call.enqueue ( new Callback<GetDriverDetailsResponse> ( ) {
            @Override
            public void onResponse ( Call<GetDriverDetailsResponse> call, Response<GetDriverDetailsResponse> response ) {

                if ( response.isSuccessful ( ) ) {

                    GetDriverDetailsResponse driverDetailsResponse = response.body ( );
                    List<GetDriverDetailData> detailData = driverDetailsResponse.getData ( );

                    if ( detailData != null ) {
                        for (GetDriverDetailData data : detailData) {

                            String Drname = data.getName ( );
                            String DrNumber = data.getMobile ( );

                            DNname.setText ( Drname );
                            DNnumber.setText ( DrNumber );

                            if ( data.getDriver_image ( ).isEmpty ( ) ) {

                                DNaccount_image.setImageResource ( R.drawable.ic_account_circle );

                            } else {

                                Picasso.with ( DriverHomePageActivity.this ).load ( data.getDriver_image ( ) ).into ( DNaccount_image );
                            }


                        }

                        Toast.makeText ( DriverHomePageActivity.this, "Success", Toast.LENGTH_SHORT ).show ( );
                    } else {
                        Toast.makeText ( DriverHomePageActivity.this, "Driver Nav UnSucess", Toast.LENGTH_SHORT ).show ( );
                    }
                }
            }

            @Override
            public void onFailure ( Call<GetDriverDetailsResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again Later", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverHomePageActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }

    @Override
    protected void onStart ( ) {
        super.onStart ( );

        // here we are calling this method to initalize the FusedLocation Apis to get Updated Current Location
        intalizeFusedLocation ( );

        // hiding tripcancle and tripCompleted buttom
        DriverButtonLayout.setVisibility ( View.GONE );


        startUpdateLocationThread ( );


        startGetUserDetailsThread ();

//        if (!DriverSharePrefManager.getInstance(this).DriverAlreadyLoggedIn()) {
//
//            Intent intent = new Intent(DriverHomePageActivity.this, UserHomePageActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    public boolean onNavigationItemSelected ( @NonNull MenuItem menuItem ) {

        switch (menuItem.getItemId ( )) {

            case R.id.driver_booking_history:
                Intent intentDriverHistory = new Intent ( DriverHomePageActivity.this, DriverTipHistoryActivity.class );
                startActivity ( intentDriverHistory );
                break;

            case R.id.driverNotification:
                Intent intentDriverNotification = new Intent ( DriverHomePageActivity.this, DriverNotificationActivity.class );
                startActivity ( intentDriverNotification );
                break;

            case R.id.driver_setting:
                Intent intent = new Intent ( DriverHomePageActivity.this, DriverAccountSettingActivity.class );
                startActivity ( intent );
                break;

            case R.id.driver_share:
                Intent intentDriverShare = new Intent ( DriverHomePageActivity.this, ShareAppActivity.class );
                startActivity ( intentDriverShare );
                break;

            case R.id.driver_Contact:

                Intent intentContactUs = new Intent ( DriverHomePageActivity.this, CustomerCareActivity.class );
                startActivity ( intentContactUs );
                break;

            case R.id.driver_terms:

                Intent intentTerms = new Intent ( DriverHomePageActivity.this, UserTermsConditionActivity.class );
                startActivity ( intentTerms );
                break;

            case R.id.driverAbout:
                Intent intentAboutUS = new Intent ( DriverHomePageActivity.this, AboutUsActivity.class );
                startActivity ( intentAboutUS );

                break;


            case R.id.User:
                Intent intentUser = new Intent ( DriverHomePageActivity.this, UserHomePageActivity.class );
                startActivity ( intentUser );

                break;

            case R.id.driverStoreRate:

                break;

            case R.id.driverLogout:
                DriverSignout ( );
                break;

        }
        return true;
    }


    public void DriverSignout ( ) {
        //we are callin Logout Method from SharePrefManager will will delet all user detail from share prefrences
        DriverSharePrefManager.getInstance ( DriverHomePageActivity.this ).DriverLogout ( );
        Toast.makeText ( this, "SignOut", Toast.LENGTH_SHORT ).show ( );
        Intent intent = new Intent ( DriverHomePageActivity.this, UserHomePageActivity.class );
        startActivity ( intent );
    }


    //This is for Setting Map on homePage Fragment
    @Override
    public void onConnected ( @Nullable Bundle bundle ) {

        mLocationRequest = new LocationRequest ( );
        mLocationRequest.setInterval ( 1000 );
        mLocationRequest.setFastestInterval ( 1000 );
        mLocationRequest.setPriority ( LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY );
        if ( ContextCompat.checkSelfPermission ( this,
                Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED ) {
            LocationServices.FusedLocationApi.requestLocationUpdates ( mGoogleApiClient, mLocationRequest, this );
        }

    }

    @Override
    public void onConnectionSuspended ( int i ) {

    }

    @Override
    public void onConnectionFailed ( @NonNull ConnectionResult connectionResult ) {

    }

    @Override
    public void onLocationChanged ( Location location ) {

        mLastLocation = location;
        if ( mCurrLocationMarker != null ) {
            mCurrLocationMarker.remove ( );
        }
        //Place current location marker
        CurrentLatLng = new LatLng ( location.getLatitude ( ), location.getLongitude ( ) );
        MarkerOptions markerOptions = new MarkerOptions ( );
        markerOptions.position ( CurrentLatLng );
        markerOptions.title ( "Current Position" );

        //remove below comment to add marker to current location
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera ( CameraUpdateFactory.newLatLng ( CurrentLatLng ) );
        mMap.animateCamera ( CameraUpdateFactory.zoomTo ( 14 ) );


        if ( CurrentLatLng.equals ( CurrentLatLng ) ) {

            Toast.makeText ( this, "My Current Location" + CurrentLatLng.toString ( ), Toast.LENGTH_SHORT ).show ( );
        }


        //Getting lattitude and Longitude and passing to getAddress Method
        latitude = location.getLatitude ( );
        longitude = location.getLongitude ( );
        getAddress ( this, latitude, longitude );

        //stop location updates
        if ( mGoogleApiClient != null ) {
            LocationServices.FusedLocationApi.removeLocationUpdates ( mGoogleApiClient, this );
        }
    }


    @Override
    public void onMapReady ( GoogleMap googleMap ) {

        mMap = googleMap;

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if ( ContextCompat.checkSelfPermission ( this,
                    Manifest.permission.ACCESS_FINE_LOCATION )
                    == PackageManager.PERMISSION_GRANTED ) {
                buildGoogleApiClient ( );
                mMap.setMyLocationEnabled ( true );
            }
        } else {
            buildGoogleApiClient ( );
            mMap.setMyLocationEnabled ( true );
        }

        //get LatLang of Center of Map
        mMap.setOnCameraIdleListener ( this );


        //Enableling location button for that we must have  location permission
        if ( ContextCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED ) {
            //enabling the location button
            mMap.setMyLocationEnabled ( true );
            mMap.getUiSettings ( ).setMyLocationButtonEnabled ( true );


            // My loaction button is by default in top - right
            //to make My Location Button in Buttom
            if ( mapView != null && mapView.findViewById ( Integer.parseInt ( "1" ) ) != null ) {

                View locationButton = (( View ) mapView.findViewById ( Integer.parseInt ( "1" ) )
                        .getParent ( )).findViewById ( Integer.parseInt ( "2" ) );

                //we are fetching layoutParam of location Button
                RelativeLayout.LayoutParams layoutParams = ( RelativeLayout.LayoutParams ) locationButton.getLayoutParams ( );

                // removing location button from top
                layoutParams.addRule ( RelativeLayout.ALIGN_PARENT_TOP, 0 );

                //Setting Location button to Bottom
                layoutParams.addRule ( RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE );

                //Setting margin to locationButton
                layoutParams.setMargins ( 0, 0, 40, 180 );
            }

            mMap.setOnMyLocationButtonClickListener ( new GoogleMap.OnMyLocationButtonClickListener ( ) {
                @Override
                public boolean onMyLocationButtonClick ( ) {
                    // true bcz when user click on location button
                    // pickLocation EditText should change to current location
                    DriverPickupMarker.setVisibility ( View.VISIBLE );

                    return false;
                }
            } );
        }

        // Check if gps is Enabled or not and request user to Enable it
        LocationRequest locationRequest = LocationRequest.create ( );
        locationRequest.setInterval ( 10000 );
        locationRequest.setFastestInterval ( 5000 );
        locationRequest.setPriority ( LocationRequest.PRIORITY_HIGH_ACCURACY );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder ( )
                .addLocationRequest ( locationRequest );

        SettingsClient settingsClient = LocationServices.getSettingsClient ( DriverHomePageActivity.this );
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings ( builder.build ( ) );

        //this OnSuccessListener is Calle if Gps is already on
        task.addOnSuccessListener ( this, new OnSuccessListener<LocationSettingsResponse> ( ) {
            @Override
            public void onSuccess ( LocationSettingsResponse locationSettingsResponse ) {

            }
        } );

        //this OnFailureListner is called wen Gps is not enabled
        task.addOnFailureListener ( this, new OnFailureListener ( ) {
            @Override
            public void onFailure ( @NonNull Exception e ) {

                //here we will check if Issue is Resolvable
                //if it is resolvabe like it turnOff her we will enable the Gps
                if ( e instanceof ResolvableApiException ) {

                    ResolvableApiException resolvable = ( ResolvableApiException ) e;

                    try {
                        //here in second parameter u can use and number that will use as Request_Code for this starting Activity
                        //Basically this will show user a Dialog Enable the gps in whic he can enable the location or deny
                        resolvable.startResolutionForResult ( DriverHomePageActivity.this, 51 );
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace ( );
                    }
                }

            }
        } );

        mMap.setOnMyLocationButtonClickListener ( new GoogleMap.OnMyLocationButtonClickListener ( ) {
            @Override
            public boolean onMyLocationButtonClick ( ) {

                //move map camera
                mMap.moveCamera ( CameraUpdateFactory.newLatLng ( CurrentLatLng ) );
                mMap.animateCamera ( CameraUpdateFactory.zoomTo ( 14 ) );

                return true;
            }
        } );


    }


    protected synchronized void buildGoogleApiClient ( ) {
        mGoogleApiClient = new GoogleApiClient.Builder ( this )
                .addConnectionCallbacks ( this )
                .addOnConnectionFailedListener ( this )
                .addApi ( LocationServices.API ).build ( );
        mGoogleApiClient.connect ( );
    }


    //get LatLang of Map from center of screen
    // write this in onMapReady    mMap.setOnCameraIdleListener(this);
    @Override
    public void onCameraIdle ( ) {

        LatLng center = mMap.getCameraPosition ( ).target;
        double lat = center.latitude;
        double longt = center.longitude;

        getAddress ( this, lat, longt );

        // Toast.makeText(this, String.valueOf(longt), Toast.LENGTH_SHORT).show();

    }


    // Getting Addtress from Latitude and Longitude
    public void getAddress ( Context context, double LATITUDE, double LONGITUDE ) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder ( context, Locale.getDefault ( ) );
            List<Address> addresses = geocoder.getFromLocation ( LATITUDE, LONGITUDE, 1 );
            if ( addresses != null && addresses.size ( ) > 0 ) {


                address = addresses.get ( 0 ).getAddressLine ( 0 ); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get ( 0 ).getLocality ( );
                String premises = addresses.get ( 0 ).getPremises ( );
                String subLocality = addresses.get ( 0 ).getSubLocality ( );
                String state = addresses.get ( 0 ).getAdminArea ( );
                String country = addresses.get ( 0 ).getCountryName ( );
                postalCode = addresses.get ( 0 ).getPostalCode ( );
                String knownName = addresses.get ( 0 ).getFeatureName ( ); // Only if available else return NULL


                // setting location in driverPickupLocation EditText when driverPickupLocation editext is clicked
                if ( isValid == true ) {

                    //setting current location in driverPickupLocation EditText
                    driverCurrentLocation.setText ( address );

                    // setting location in driverDropLocation EditText when driverDropLocation editext is clicked
                } else if ( isValid == false ) {
                }

            }
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        return;
    }


    //AutoSuggetion
    public void autoCompleteLocation ( ) {

        if ( (! Places.isInitialized ( )) ) {
            //AIzaSyBXK7L6hjtJiE41Jyelx23ir30-4hx1Zsc

            Places.initialize ( this, "AIzaSyBXK7L6hjtJiE41Jyelx23ir30-4hx1Zsc" );
            placesClient = Places.createClient ( this );

        } else {

            // Set the fields to specify which types of place data to return.
            List<Place.Field> fields = Arrays.asList ( Place.Field.ID, Place.Field.ADDRESS,
                    Place.Field.NAME, Place.Field.LAT_LNG );

            // Start the autocomplete intent.

            //Add this line in Import otherWise build will give error
            //import com.google.android.libraries.places.widget.Autocomplete;

            Intent autoComplete = new Autocomplete.IntentBuilder ( AutocompleteActivityMode.FULLSCREEN,
                    fields ).setCountry ( "IN" )
                    .build ( this );

            startActivityForResult ( autoComplete, AUTOCOMPLETE_REQUEST_CODE );
        }
    }


    /**
     * Override the activity's onActivityResult(), check the request code, and
     * do something with the returned place data
     */

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult ( requestCode, resultCode, data );

        if ( requestCode == AUTOCOMPLETE_REQUEST_CODE ) {

            if ( resultCode == RESULT_OK ) {

                Place place = Autocomplete.getPlaceFromIntent ( data );

                Toast.makeText ( DriverHomePageActivity.this, "Lat Long: " + place.getLatLng ( ), Toast.LENGTH_LONG ).show ( );

                pickup = place.getLatLng ( );

                //settin Address in userDropLoctation |TextView
                driverPickUpLocation.setText ( place.getAddress ( ) );

//                DriverDropMarker.setVisibility(View.VISIBLE);
                DriverPickupMarker.setVisibility ( View.GONE );

                MarkerOptions markerOptions = new MarkerOptions ( );
                markerOptions.position ( place.getLatLng ( ) );
                markerOptions.title ( "Drop Location" );

                //remove below comment to add marker to current location
                //  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mDropLocationMarker = mMap.addMarker ( markerOptions );

                //move map camera
                if ( isValid == false ) {

                    mMap.moveCamera ( CameraUpdateFactory.newLatLng ( place.getLatLng ( ) ) );
                    mMap.animateCamera ( CameraUpdateFactory.zoomTo ( 14 ) );
                }
                String address = place.getAddress ( );


                // do query with address

            } else if ( resultCode == AutocompleteActivity.RESULT_ERROR ) {

                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent ( data );

                Toast.makeText ( this, "Error: " + status.getStatusMessage ( ), Toast.LENGTH_LONG ).show ( );
                // Log.i(TAG, status.getStatusMessage());

            } else if ( resultCode == RESULT_CANCELED ) {

                // The user canceled the operation.

            }
        }

    }


    // for Driection

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground ( String... url ) {

            String data = "";

            try {
                data = downloadUrl ( url[ 0 ] );
            } catch (Exception e) {
                Log.d ( "Background Task", e.toString ( ) );
            }
            return data;
        }

        @Override
        protected void onPostExecute ( String result ) {
            super.onPostExecute ( result );

            ParserTask parserTask = new ParserTask ( );

            parserTask.execute ( result );

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground ( String... jsonData ) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject ( jsonData[ 0 ] );
                DirectionsJSONParser parser = new DirectionsJSONParser ( );

                routes = parser.parse ( jObject );
            } catch (Exception e) {
                e.printStackTrace ( );
            }
            return routes;
        }

        @Override
        protected void onPostExecute ( List<List<HashMap<String, String>>> result ) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions ( );

            for (int i = 0; i < result.size ( ); i++) {
                points = new ArrayList ( );
                lineOptions = new PolylineOptions ( );

                List<HashMap<String, String>> path = result.get ( i );

                for (int j = 0; j < path.size ( ); j++) {
                    HashMap<String, String> point = path.get ( j );

                    double lat = Double.parseDouble ( point.get ( "lat" ) );
                    double lng = Double.parseDouble ( point.get ( "lng" ) );
                    LatLng position = new LatLng ( lat, lng );

                    points.add ( position );


                }

                lineOptions.addAll ( points );
                lineOptions.width ( 12 );
                lineOptions.color ( Color.BLUE );
                lineOptions.geodesic ( true );

            }

// Drawing polyline in the Google Map for the i-th route

            mMap.addPolyline ( lineOptions );

            if ( lineOptions != null ) {

                // this zoom method is called when ployline will be drawn between two point
                zoomRoute ( lineOptions.getPoints ( ) );

                DriverPickupMarker.setVisibility ( View.GONE );
//
//                mMap.addMarker(new MarkerOptions().position(boomMandir)
//                        .title("Drop Point")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.drop_marker)));

                mMap.addMarker ( new MarkerOptions ( ).position ( CurrentLatLng )
                        .title ( "Pickup Point" )
                        .icon ( BitmapDescriptorFactory.defaultMarker ( BitmapDescriptorFactory.HUE_GREEN ) ) );


            }
        }
    }


    // this is to zoom in polyLine when Driver Accept booking
    public void zoomRoute ( List<LatLng> lstLatLngRoute ) {

        if ( mMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty ( ) ) return;

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder ( );
        for (LatLng latLngPoint : lstLatLngRoute)
            boundsBuilder.include ( latLngPoint );

        int routePadding = 200;
        LatLngBounds latLngBounds = boundsBuilder.build ( );


        mMap.animateCamera (
                CameraUpdateFactory.newLatLngBounds ( latLngBounds, routePadding ),
                1200,
                null
        );
    }

    private String getDirectionsUrl ( LatLng origin, LatLng dest ) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyBqLcfinSUMXG8FmyA3Ek9gk-ydXeGSSjs";


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl ( String strUrl ) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL ( strUrl );

            urlConnection = ( HttpURLConnection ) url.openConnection ( );

            urlConnection.connect ( );

            iStream = urlConnection.getInputStream ( );

            BufferedReader br = new BufferedReader ( new InputStreamReader ( iStream ) );

            StringBuffer sb = new StringBuffer ( );

            String line = "";
            while ((line = br.readLine ( )) != null) {
                sb.append ( line );
            }

            data = sb.toString ( );

            br.close ( );

        } catch (Exception e) {
            Log.d ( "Exception", e.toString ( ) );
        } finally {
            iStream.close ( );
            urlConnection.disconnect ( );
        }
        return data;
    }


    // checking that it contain data sended form UpdateUserName Activity
    public void getDNavNameFromUpdateName ( ) {

        Bundle bundle = getIntent ( ).getExtras ( );

        if ( getIntent ( ).hasExtra ( "moblNum" ) ) {

            String updateUserName = bundle.getString ( "userNavName" );

            DNname.setText ( updateUserName );
        }

    }


    // the Driver click on On Switch it will make Drive Avilable
    private void makeDriverOnline ( ) {

        String avilable = "checked";

        // when driver will click on this Online Button then Driver Location will start updating
        startUpdateLocationThread ( );

        DriverLoginData loginData = DriverSharePrefManager.getInstance ( DriverHomePageActivity.this ).getDriverDetail ( );
        String driver_Id = loginData.getDriver_id ( );

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
        Call<UpdateDriverAviabilityResponse> call = ltgApi.updateDriverAvilability ( driver_Id, avilable );

        call.enqueue ( new Callback<UpdateDriverAviabilityResponse> ( ) {
            @Override
            public void onResponse ( Call<UpdateDriverAviabilityResponse> call, Response<UpdateDriverAviabilityResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {
                    Toast.makeText ( DriverHomePageActivity.this, "Online", Toast.LENGTH_SHORT ).show ( );

                    // when driver will press online btn then this method will execute
                    // it will save driver current location to database
                    PostDriverCurrentLocation ( );


                } else {

                    Toast.makeText ( DriverHomePageActivity.this, "Make Driver Online Unsucess", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<UpdateDriverAviabilityResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverHomePageActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


    // the Driver click on On Switch it will make Drive Offline

    private void makeDriverOffline ( ) {

        String Notavilable = "unchecked";

        DriverLoginData loginData = DriverSharePrefManager.getInstance ( DriverHomePageActivity.this ).getDriverDetail ( );
        String driver_Id = loginData.getDriver_id ( );

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
        Call<UpdateDriverAviabilityResponse> call = ltgApi.updateDriverAvilability ( driver_Id, Notavilable );
        call.enqueue ( new Callback<UpdateDriverAviabilityResponse> ( ) {
            @Override
            public void onResponse ( Call<UpdateDriverAviabilityResponse> call, Response<UpdateDriverAviabilityResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {

                    Toast.makeText ( DriverHomePageActivity.this, "Offline", Toast.LENGTH_SHORT ).show ( );
                } else {
                    Toast.makeText ( DriverHomePageActivity.this, "make Driver offline UnSucess", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<UpdateDriverAviabilityResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverHomePageActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );

    }


    // starting The location Update
    // here this method is used to initalize the FusedLocation Apis to get Updated Current Location
    public void intalizeFusedLocation ( ) {

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient ( DriverHomePageActivity.this );

        msettingsClient = LocationServices.getSettingsClient ( this );

        locationCallback = new LocationCallback ( ) {

            @Override
            public void onLocationResult ( LocationResult locationResult ) {
                super.onLocationResult ( locationResult );


                currentlocation = locationResult.getLastLocation ( );
            }
        };

        //        creating new location request
        locationRequest = new LocationRequest ( );

//        setting the updating request interval
        locationRequest.setInterval ( UPDATE_IN_MILL );

//        setting the updating request interval
        locationRequest.setFastestInterval ( FAST_IN_MILL );

//        setting the location accuracy
        locationRequest.setPriority ( LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder ( );

        builder.addLocationRequest ( locationRequest );
        locationSettingsRequest = builder.build ( );

    }

    public void getCurrentLocation ( ) {

        msettingsClient.checkLocationSettings ( locationSettingsRequest )
                .addOnSuccessListener ( this, new OnSuccessListener<LocationSettingsResponse> ( ) {
                    @Override
                    public void onSuccess ( LocationSettingsResponse locationSettingsResponse ) {

                        mfusedLocationProviderClient.requestLocationUpdates ( locationRequest, locationCallback, Looper.myLooper ( ) );

                        if ( currentlocation != null ) {

                            //txt_show.setText("Lat"+currentlocation.getLatitude()+" " +"Lag"+currentlocation.getLongitude());

                            sendDriverLatitude = currentlocation.getLatitude ( );

                            sendDriverLongitude = currentlocation.getLongitude ( );

                            getAddress ( DriverHomePageActivity.this, currentlocation.getLatitude ( ), currentlocation.getLongitude ( ) );

                            PostDriverCurrentLocation ( );

                            Toast.makeText ( DriverHomePageActivity.this, sendDriverLatitude + " " + sendDriverLongitude + "", Toast.LENGTH_SHORT ).show ( );
                        }

                    }
                } ).addOnFailureListener ( this, new OnFailureListener ( ) {
            @Override
            public void onFailure ( @NonNull Exception e ) {

                int code = (( ApiException ) e).getStatusCode ( );

                switch (code) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        ResolvableApiException eae = ( ResolvableApiException ) e;

                        try {
                            eae.startResolutionForResult ( DriverHomePageActivity.this, code );
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace ( );
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText ( DriverHomePageActivity.this, "check your settings", Toast.LENGTH_SHORT ).show ( );
                        break;
                }
            }
        } );

    }


    // when driver is click on online button then his current location will be updated in database

    private void PostDriverCurrentLocation ( ) {


        String latlang = sendDriverLatitude + "," + sendDriverLongitude;
        String lat = String.valueOf ( sendDriverLatitude );
        String lang = String.valueOf ( sendDriverLongitude );
        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
//
//        Toast.makeText(this, "number : " + Number + " " + "address : " + address + " " + "postalCode : " + postalCode + " " + "latitude : "
//                + lat + " longitude : " + lang + " latLang : " + latlang, Toast.LENGTH_SHORT).show();

        Call<PostDriverCurrentLocationResponse> call = ltgApi.postDriverLocation ( driverId, address, postalCode, lat, lang, latlang );

        call.enqueue ( new Callback<PostDriverCurrentLocationResponse> ( ) {
            @Override
            public void onResponse ( Call<PostDriverCurrentLocationResponse> call, Response<PostDriverCurrentLocationResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {

                    PostDriverCurrentLocationResponse locationResponse = response.body ( );
                    String num = locationResponse.getData ( ).getMobile ( );
                    String address = locationResponse.getData ( ).getAddress ( );
                    String postalCode = locationResponse.getData ( ).getPincode ( );
                    String lat = locationResponse.getData ( ).getLattitude ( );
                    String lang = locationResponse.getData ( ).getLongitude ( );
                    String latlang = locationResponse.getData ( ).getLatlang ( );

                    // if driver accept the booking it will first send the Post current location and the send the current location
                    if ( isAcceptBooking ) {

                        //sending driver current location to booked database
                        sendDriverLocation ( );
                        Toast.makeText ( DriverHomePageActivity.this, "Driver Accepted the Boking", Toast.LENGTH_SHORT ).show ( );
                    }

                    Toast.makeText ( DriverHomePageActivity.this, "number : " + num + " " + "address : " + address + " " + "postalCode : " + postalCode + " " + "latitude : "
                            + lat + " longitude : " + lang + " latLang : " + latlang, Toast.LENGTH_SHORT ).show ( );

                    Toast.makeText ( DriverHomePageActivity.this, "Post Driver Location Executed Sucessfully", Toast.LENGTH_SHORT ).show ( );
                } else {

                    Toast.makeText ( DriverHomePageActivity.this, "Post Driver Location Unsucessfull", Toast.LENGTH_SHORT ).show ( );
                }

            }

            @Override
            public void onFailure ( Call<PostDriverCurrentLocationResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverHomePageActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


    //fetch user location and show in Dialog box
    public void getUserDetails ( ) {

        DriverLoginData loginData = DriverSharePrefManager.getInstance ( DriverHomePageActivity.this ).getDriverDetail ( );
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

                            Toast.makeText ( DriverHomePageActivity.this, "Trip Status" + getUserTripStatus, Toast.LENGTH_SHORT ).show ( );

                            if ( getUserTripStatus.equals ( "requested" ) ) {

                                acceptBookingDialog ( Uname, Umobile, Uaddress );
                            }


                        }


                    } else {
                        Toast.makeText ( DriverHomePageActivity.this, "user Data is Null", Toast.LENGTH_SHORT ).show ( );
                    }

                } else {

                    Toast.makeText ( DriverHomePageActivity.this, "Gert User Location Unsucess", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<GetUserLocationResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
            }
        } );

    }


    public void acceptBookingDialog ( String userName, String userMobile, final String userAddress ) {

        openGetUserDialogBox = false;

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById ( android.R.id.content );

        //then we will inflate the custom alert dialog xml that we created
        final View view = LayoutInflater.from ( this ).inflate ( R.layout.accept_booking_dialog_box, viewGroup, false );


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder ( this );

        //setting the view of the builder to our custom view that we already inflated
        builder.setView ( view );

        ImageView userBookingImage = view.findViewById ( R.id.userBookingImage );

        TextView userBookingName = view.findViewById ( R.id.userBookingName );

        TextView userBookingNumber = view.findViewById ( R.id.userBookingNumber );

        TextView PickupLocation = view.findViewById ( R.id.PickupLocation );

        TextView CurrentLocation = view.findViewById ( R.id.CurrentLocation );

        final Button acceptBooking = view.findViewById ( R.id.acceptBooking );

        Button cancleBooking = view.findViewById ( R.id.cancleBooking );

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create ( );
        //add this line to make your dialogbox radius round
        alertDialog.getWindow ( ).setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT ) );

        alertDialog.getWindow ( ).setGravity ( Gravity.BOTTOM );

        WindowManager.LayoutParams layoutParams = alertDialog.getWindow ( ).getAttributes ( );

        layoutParams.y = 170; // bottom margin

        alertDialog.getWindow ( ).setAttributes ( layoutParams );

        alertDialog.setCanceledOnTouchOutside ( false );

        userBookingName.setText ( userName );
        userBookingNumber.setText ( userMobile );
        PickupLocation.setText ( userAddress );
        CurrentLocation.setText ( address );

        cancleBooking.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                cancleBooking ( );
                alertDialog.dismiss ( );
            }
        } );

        DriverCancleTrip.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                showCancleTripConfirmationDialog ( );

            }
        } );

        acceptBooking.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                Toast.makeText ( DriverHomePageActivity.this, "Accept booking button clicked", Toast.LENGTH_SHORT ).show ( );

                isAcceptBooking = true;
                // send driver current Location to database
                PostDriverCurrentLocation ( );

                // Getting URL to the Google Directions API
                LatLng userLatLang = new LatLng ( userLat, userLang );

                String url = getDirectionsUrl ( CurrentLatLng, userLatLang );

                DownloadTask downloadTask = new DownloadTask ( );

                // Start downloading json data from Google Directions API
                downloadTask.execute ( url );

                driverPickUpLocation.setText ( userAddress );

                driverPickUpLocation.setVisibility ( View.VISIBLE );

                alertDialog.dismiss ( );

                // making Cancle and completeed button Visible
                DriverButtonLayout.setVisibility ( View.VISIBLE );
            }
        } );


        alertDialog.show ( );
    }


    private void showCancleTripConfirmationDialog ( ) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder ( DriverHomePageActivity.this );
        builder1.setTitle ( "Trip Cancellation" );
        builder1.setMessage ( "Are you sure you want to cancel the trip?" );
        builder1.setCancelable ( true );

        builder1.setPositiveButton (
                "Yes",
                new DialogInterface.OnClickListener ( ) {
                    public void onClick ( DialogInterface dialog, int id ) {

                        Intent intent = new Intent ( DriverHomePageActivity.this, CancleBookingReasonActivity.class );
                        startActivity ( intent );
                        finish ( );
                    }
                } );

        builder1.setNegativeButton (
                "No",
                new DialogInterface.OnClickListener ( ) {
                    public void onClick ( DialogInterface dialog, int id ) {
                        dialog.cancel ( );
                    }
                } );

        AlertDialog alert11 = builder1.create ( );
        alert11.show ( );
    }


    private void cancleBooking ( ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
        Call<BookingStatusResponse> call = ltgApi.cancleBooking ( driverId, "cancelled" );
        call.enqueue ( new Callback<BookingStatusResponse> ( ) {
            @Override
            public void onResponse ( Call<BookingStatusResponse> call, Response<BookingStatusResponse> response ) {

                BookingStatusResponse bookingResponse = response.body ( );
                String status = bookingResponse.getData ( ).getStatus ( );

                Toast.makeText ( DriverHomePageActivity.this, "Status : " + status, Toast.LENGTH_SHORT ).show ( );

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) && bookingResponse.getData ( ).getStatus ( ).equals ( "1" ) ) {

                    Snackbar.make ( findViewById ( android.R.id.content ), "Your Trip is Cancelled", Snackbar.LENGTH_LONG )
                            .setAction ( "OK", null )
                            .setDuration ( 5000 )
                            .setActionTextColor ( Color.WHITE ).show ( );

                } else {

                    Toast.makeText ( DriverHomePageActivity.this, "cancle Booking UnSucessful", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<BookingStatusResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverHomePageActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


    private void BookingCompleted ( ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );
        Call<BookingStatusResponse> call = ltgApi.tripCompleted ( driverId, "completed" );
        call.enqueue ( new Callback<BookingStatusResponse> ( ) {
            @Override
            public void onResponse ( Call<BookingStatusResponse> call, Response<BookingStatusResponse> response ) {

                BookingStatusResponse bookingResponse = response.body ( );
                String status = bookingResponse.getData ( ).getStatus ( );

                Toast.makeText ( DriverHomePageActivity.this, "Status : " + status, Toast.LENGTH_SHORT ).show ( );

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) && bookingResponse.getData ( ).getStatus ( ).equals ( "1" ) ) {

                    Snackbar.make ( findViewById ( android.R.id.content ), "Your Trip is Completed", Snackbar.LENGTH_LONG )
                            .setAction ( "OK", null )
                            .setDuration ( 5000 )
                            .setActionTextColor ( Color.WHITE ).show ( );

                } else {

                    Toast.makeText ( DriverHomePageActivity.this, "booking Completed UnSucessful", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<BookingStatusResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverHomePageActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


    private void sendDriverLocation ( ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        Call<SendDriverLocationResponse> call = ltgApi.sendDriverLocation ( Umobile, driverId );

        call.enqueue ( new Callback<SendDriverLocationResponse> ( ) {
            @Override
            public void onResponse ( Call<SendDriverLocationResponse> call, Response<SendDriverLocationResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {

                    SendDriverLocationResponse sendDriverLocationResponse = response.body ( );
                    String status = sendDriverLocationResponse.getData ( ).getStatus ( );

                    if ( status.equals ( "1" ) ) {

                        Toast.makeText ( DriverHomePageActivity.this, "Sucessfull", Toast.LENGTH_SHORT ).show ( );
                    } else {

                        Toast.makeText ( DriverHomePageActivity.this, "Send Driver location Failed", Toast.LENGTH_SHORT ).show ( );
                    }
                } else {
                    Toast.makeText ( DriverHomePageActivity.this, "Send Driver Location Unsucess", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<SendDriverLocationResponse> call, Throwable t ) {

                Toast.makeText ( DriverHomePageActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( DriverHomePageActivity.this, "UnSucess", Toast.LENGTH_SHORT ).show ( );
            }
        } );

    }


    public void startUpdateLocationThread ( ) {

        stopthread = false;

        updateLocationThread runnableThread = new updateLocationThread ( );
        new Thread ( runnableThread ).start ( );

        Toast.makeText ( this, "Update Location Thread Started", Toast.LENGTH_SHORT ).show ( );

    }

    public void stopUpdateLocationThread ( ) {

        stopthread = true;

        Toast.makeText ( this, "Update Location Thread Stopped", Toast.LENGTH_SHORT ).show ( );
    }


    // creating the Thread For sendind user Location to database
    class updateLocationThread implements Runnable {
        Handler handler = new Handler ( );

        @Override
        public void run ( ) {

            final Runnable runnable = new Runnable ( ) {
                @Override
                public void run ( ) {

                    if ( stopthread == false ) {

                        //Initiate your API here
                        handler.postDelayed ( this, 5000 );

                        if ( DriverIsOnline ) {

                            // from get location we have to execute the PostDriver Location
                            getCurrentLocation ( );

                        }

                    }

                }
            };
            handler.postDelayed ( runnable, 5000 );
        }


    }


    public void startGetUserDetailsThread ( ) {

        stopUserDetailthread = false;

        GetUserDetailsThread runnableThread = new GetUserDetailsThread ( );
        new Thread ( runnableThread ).start ( );

        Toast.makeText ( this, "GetUser Detail Thread Started", Toast.LENGTH_SHORT ).show ( );

    }

    public void stopGetUserDetailsThread ( ) {

        stopUserDetailthread = true;

    }


    // Creating Thread to GetUserDetail and Show in DialoBox

    class GetUserDetailsThread implements Runnable {

        Handler handler = new Handler ( );

        @Override
        public void run ( ) {

            final Runnable runnable = new Runnable ( ) {
                @Override
                public void run ( ) {

                    if ( stopthread == false ) {

                        //Initiate your API here
                        handler.postDelayed ( this, 5000 );

                        if ( DriverIsOnline ) {

                            // from get location we have to execute the PostDriver Location
                            getUserDetails ( );
                        }
                    }

                }
            };
            handler.postDelayed ( runnable, 5000 );
        }

    }


    @Override
    protected void onResume ( ) {
        super.onResume ( );

//        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();

        if ( getIntent ( ).hasExtra ( "abc" ) ) {

            Bundle bundle = getIntent ( ).getExtras ( );

            String a = bundle.getString ( "abc" );

            //  acceptBookingDialog();
        }
    }


    @Override
    protected void onDestroy ( ) {
        super.onDestroy ( );

        stopUpdateLocationThread ( );

        stopGetUserDetailsThread ();
    }
}

