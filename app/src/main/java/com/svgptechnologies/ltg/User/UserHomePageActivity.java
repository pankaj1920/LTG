package com.svgptechnologies.ltg.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.svgptechnologies.ltg.CheckInternetConnectionActivity;
import com.svgptechnologies.ltg.CustomerCareActivity;
import com.svgptechnologies.ltg.Driver.DriverHomePageActivity;
import com.svgptechnologies.ltg.Driver.DriverLoginActivity;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.GetUserDetail.GetUserDetailData;
import com.svgptechnologies.ltg.Json.UserJson.GetUserDetail.GetUserDetailResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginData;
import com.svgptechnologies.ltg.Json.UserJson.VechileCategory.VechileCategoryResponse;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.ShareAppActivity;
import com.svgptechnologies.ltg.SharedPrefrences.DriverSharePrefManager;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.SeachDriver.SearchDriverActivity;
import com.svgptechnologies.ltg.User.VechileCategory.VechileCategoryAdapter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker, DropLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    LatLng latLng, DropLatLong;
    private DrawerLayout user_drawer_layout;
    public static TextView userDropLocation, userPickupLocation;
    double latitude, longitude;
    TextView UNname, UNmobile;
    View mapView;
    VechileCategoryAdapter adapter;
    RecyclerView VichleCategoryRecycle;
    private ImageView UserPickupMarker, UserDropMarker;
    private CircleImageView UNaccountImage;
    private Boolean isValid;
    Button UserBtnBookLater, UserBtnBookNow;
    String SelectedVechileName;

    ShimmerFrameLayout veichleCategorySimmer;

    String address, Pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);


        Toolbar userHomeToolbar = findViewById(R.id.userHomeToolbar);
        userHomeToolbar.setTitle(R.string.letGo);
        setSupportActionBar(userHomeToolbar);


        veichleCategorySimmer = findViewById(R.id.veichleCategorySimmer);

        UserBtnBookNow = findViewById(R.id.UserBtnBookNow);

        userPickupLocation = findViewById(R.id.userPickupLocation);

        UserPickupMarker = findViewById(R.id.UserPickupMarker);


        VichleCategoryRecycle = findViewById(R.id.VichleCategoryRecycle);

        //Setting the Vechile Category Recycle
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        VichleCategoryRecycle.setLayoutManager(layoutManager);


        //checking the inernet connection
        checkInternetConnection();

        // calling Vechile Category method to display vichel category
        vechileCategory();


        //mean whenever user will drag the map by default address will change in pickupLocation EditText
        //isValid = true;

        user_drawer_layout = findViewById(R.id.user_drawer_layout);

        //to handel the click event of navigation view we need refrence of navigation view
        NavigationView navigationView = findViewById(R.id.user_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //to get drawerIcon to open Nav Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(UserHomePageActivity.this,
                user_drawer_layout, userHomeToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //to change the toggle buttom on nav drawer
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        user_drawer_layout.addDrawerListener(toggle);
        toggle.syncState();


        // getting nav header so tthat we can change detail of navHeader
        View headerView = navigationView.getHeaderView(0);

        //Nav Header Name
        UNname = headerView.findViewById(R.id.UNname);

        //NavHeader Number
        UNmobile = headerView.findViewById(R.id.UNmobile);

        UNaccountImage = headerView.findViewById(R.id.UNaccountImage);


        //Setting UserNavDetail
        setNavDetail();
        getNavNameFromUpdateName();


        UNname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, UserAccountSettingActivity.class);
                startActivity(intent);

            }
        });

        UNmobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, UserAccountSettingActivity.class);
                startActivity(intent);
            }
        });

        UNaccountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, UserAccountSettingActivity.class);
                startActivity(intent);
            }
        });


        UserBtnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserHomePageActivity.this, SearchDriverActivity.class);

                Bundle bundle = new Bundle();

                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putString("address", address);
                bundle.putString("pincode", Pincode);
                bundle.putString("selectedVecgileName", SelectedVechileName);
                intent.putExtras(bundle);

                // when vechile is not null mean vichel is selected then it will go to SearchDriverActivity
                if (SelectedVechileName != null) {

                    startActivity(intent);
                } else {

                    // when vechile is not selected and it is null then this toast will come
                    Toast.makeText(UserHomePageActivity.this, "Select Service Type", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });


        userPickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserPickupMarker.setVisibility(View.VISIBLE);
//                UserDropMarker.setVisibility(View.GONE);

                //   isValid = false;
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Usermap);
        mapFragment.getMapAsync(this);

        //to set My Location Button at Botton
        mapView = mapFragment.getView();


    }

    public void checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                Toast.makeText(this, "Wifi Enable", Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                Toast.makeText(this, "Data Enabled", Toast.LENGTH_SHORT).show();
            }
        } else {

            Intent intent = new Intent(UserHomePageActivity.this, CheckInternetConnectionActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setNavDetail() {

        UserLoginData loginData = UserSharePrefManager.getInstance(this).getUserDetail();
        String mobNum = loginData.getMobile();

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<GetUserDetailResponse> call = ltgApi.getUserDetail(mobNum);

        call.enqueue(new Callback<GetUserDetailResponse>() {
            @Override
            public void onResponse(Call<GetUserDetailResponse> call, Response<GetUserDetailResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    GetUserDetailResponse userDetailResponse = response.body();

                    List<GetUserDetailData> userData = userDetailResponse.getData();

                    if (userData != null) {

                        for (GetUserDetailData data : userData) {

                            String navName = data.getName();
                            String navNum = data.getMobile();


                            UNname.setText(navName);

                            UNmobile.setText(navNum);

                            if (data.getImage().isEmpty()) {

                                UNaccountImage.setImageResource(R.drawable.ic_account_circle);

                            } else {

                                Picasso.with(UserHomePageActivity.this).load(data.getImage()).into(UNaccountImage);
                            }
                        }
                    }

                }


            }

            @Override
            public void onFailure(Call<GetUserDetailResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        checkInternetConnection();

        vechileCategory();

        veichleCategorySimmer.startShimmer();
        veichleCategorySimmer.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        veichleCategorySimmer.startShimmer();
        veichleCategorySimmer.setVisibility(View.VISIBLE);

        if (!UserSharePrefManager.getInstance(this).UserAlreadyLoggedIn()) {
            Intent intent = new Intent(UserHomePageActivity.this, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        veichleCategorySimmer.stopShimmer();
    }

    //when we open the navigaqtionDrawer and press back button we dont want to leave the activity
    //we have to close navDrawer

    @Override
    public void onBackPressed() {


        if (user_drawer_layout.isDrawerOpen(GravityCompat.START)) { //here it is start bcz it is in left side if it is in wright side it will ENG

            //it mean nav drawer is open and we want to close
            user_drawer_layout.closeDrawer(GravityCompat.START); //here it is start bcz it is in left side if it is in wright side it will ENG
        } else {
            //it mean nav drawer is not open
            //we leave the activity on back pressed
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.user_setting:
                Intent intentUserSetting = new Intent(UserHomePageActivity.this, UserAccountSettingActivity.class);
                startActivity(intentUserSetting);
                break;

            case R.id.user_share:
                Intent intentUserShare = new Intent(UserHomePageActivity.this, ShareAppActivity.class);
                startActivity(intentUserShare);
                break;


            case R.id.user_Contact:

                Intent intentContactUs = new Intent(UserHomePageActivity.this, CustomerCareActivity.class);
                startActivity(intentContactUs);
                break;

            case R.id.user_terms:

                Intent intentTerms = new Intent(UserHomePageActivity.this, UserTermsConditionActivity.class);
                startActivity(intentTerms);
                break;

            case R.id.userAbout:
                Intent intentAboutUS = new Intent(UserHomePageActivity.this, AboutUsActivity.class);
                startActivity(intentAboutUS);

                break;


            case R.id.Patner:

                checkDrivereAlreadyLogedin();
//                Intent intent = new Intent(UserHomePageActivity.this, DriverHomePageActivity.class);
//                startActivity(intent);

                break;

            case R.id.userLogout:
                UserSignout();
                break;


        }
        return true;
    }

    private void checkDrivereAlreadyLogedin() {
        if (DriverSharePrefManager.getInstance(this).DriverAlreadyLoggedIn()) {

            Intent intent = new Intent(UserHomePageActivity.this, DriverHomePageActivity.class);
            startActivity(intent);
            finish();
        } else {

            Intent intent = new Intent(UserHomePageActivity.this, DriverLoginActivity.class);
            startActivity(intent);
        }
    }


    public void UserSignout() {
        //we are callin Logout Method from SharePrefManager will will delet all user detail from share prefrences
        UserSharePrefManager.getInstance(UserHomePageActivity.this).UserLogout();
        DriverSharePrefManager.getInstance(UserHomePageActivity.this).DriverLogout();

        Toast.makeText(this, "SignOut", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserHomePageActivity.this, UserLoginActivity.class);
        startActivity(intent);
    }


    //This is for Setting Map on homePage Fragment
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        //get LatLang of Center of Map
        mMap.setOnCameraIdleListener(this);

        //Enableling location button for that we must have  location permission
        if (ContextCompat.checkSelfPermission(UserHomePageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //enabling the location button
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        // My loaction button is by default in top - right
        //to make My Location Button in Buttom
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1"))
                    .getParent()).findViewById(Integer.parseInt("2"));

            //we are fetching layoutParam of location Button\
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

            // removing location button from top
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);

            //Setting Location button to Bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            //Setting margin to locationButton
            layoutParams.setMargins(0, 0, 40, 300);

            // Check if gps is Enabled or not and request user to Enable it
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            SettingsClient settingsClient = LocationServices.getSettingsClient(UserHomePageActivity.this);
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

            //this OnSuccessListener is Calle if Gps is already on
            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                }
            });

            //this OnFailureListner is called wen Gps is not enabled
            task.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //here we will check if Issue is Resolvable
                    //if it is resolvabe like it turnOff her we will enable the Gps

                    if (e instanceof ResolvableApiException) {

                        ResolvableApiException resolvable = (ResolvableApiException) e;

                        try {
                            //here in second parameter u can use and number that will use as Request_Code for this starting Activity
                            //Basically this will show user a Dialog Enable the gps in whic he can enable the location or deny
                            resolvable.startResolutionForResult(UserHomePageActivity.this, 51);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            });


        }

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                UserPickupMarker.setVisibility(View.VISIBLE);

                return false;
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");

        String s = String.valueOf(latLng);


        //remove below comment to add marker to current location
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        //DropLocationMarker = mMap.addMarker(markerOptions);

        //move map camera


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));


        //Getting lattitude and Longitude and passing to getAddress Method
        //to get Address form latitude and longitude
        double lat = location.getLatitude();
        double lang = location.getLongitude();
        getAddress(this, lat, lang);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    //get LatLang of Map from center of screen
    // write this in onMapReady    mMap.setOnCameraIdleListener(this);
    @Override
    public void onCameraIdle() {

        LatLng center = mMap.getCameraPosition().target;
        latitude = center.latitude;
        longitude = center.longitude;


        getAddress(this, latitude, longitude);
        //    Toast.makeText(this, String.valueOf(longitude) + "this is long", Toast.LENGTH_SHORT).show();
    }


    // Getting Addtress from Latitude and Longitude
    public void getAddress(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String premises = addresses.get(0).getPremises();
                String subLocality = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                Pincode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                // setting location in driverPickupLocation EditText when driverPickupLocation editext is clicked


                //setting current location in driverPickupLocation EditText
                userPickupLocation.setText(address);
                Toast.makeText(context, "PinCode : " + Pincode, Toast.LENGTH_SHORT).show();

                // setting location in driverDropLocation EditText when driverDropLocation editext is clicked

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


//    //Auto Suggesiton
//    private void autoCompleteLocation() {
//
//        if (!Places.isInitialized()) {
//            Places.initialize(getApplicationContext(), "AIzaSyBXK7L6hjtJiE41Jyelx23ir30-4hx1Zsc");
//        } else {
//
//            // Set the fields to specify which types of place data to return.
//            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
//                    Place.Field.ADDRESS, Place.Field.LAT_LNG);
//
//            // Start the autocomplete intent.
//
//            //Add this line in Import otherWise build will give error
//            //import com.google.android.libraries.places.widget.Autocomplete;
//
//            Intent autoComplete = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
//                    fields).setCountry("IN")
//                    .build(this);
//
//
//            startActivityForResult(autoComplete, AUTOCOMPLETE_REQUEST_CODE);
//
//
//        }
//    }
//
//
//    /**
//     * Override the activity's onActivityResult(), check the request code, and
//     * do something with the returned place data
//     */
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//
//                Place place = Autocomplete.getPlaceFromIntent(data);
//
//                Toast.makeText(UserHomePageActivity.this, "ID: " + place.getName() + "address:" + place.getAddress() + "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
//
//                //settin Address in userDropLoctation |TextView
//                userDropLocation.setText(place.getAddress());
//
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(place.getLatLng());
//                markerOptions.title("Drop Location");
//
//                //remove below comment to add marker to current location
//               // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                DropLocationMarker = mMap.addMarker(markerOptions);
//
//                //move map camera
//                if (isValid == false) {
//
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
//                }
//
//                String address = place.getAddress();
//                // do query with address
//
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                // TODO: Handle the error.
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
//                // Log.i(TAG, status.getStatusMessage());
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//    }


    public void getNavNameFromUpdateName() {

        Bundle bundle = getIntent().getExtras();

        if (getIntent().hasExtra("moblNum")) {

            String updateUserName = bundle.getString("userNavName");

            UNname.setText(updateUserName);
        }

    }


    private void vechileCategory() {

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);
        Call<VechileCategoryResponse> call = ltgApi.getVechileCategory();

        call.enqueue(new Callback<VechileCategoryResponse>() {
            @Override
            public void onResponse(Call<VechileCategoryResponse> call, Response<VechileCategoryResponse> response) {

                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {

                    final VechileCategoryResponse categoryResponse = response.body();

                    adapter = new VechileCategoryAdapter(categoryResponse.getData());

                    VichleCategoryRecycle.setAdapter(adapter);

                    //stopping the shimmer effect
                    veichleCategorySimmer.stopShimmer();
                    veichleCategorySimmer.setVisibility(View.GONE);
                    VichleCategoryRecycle.setVisibility(View.VISIBLE);

                    // call onstructor of onVechileClickListner Interface
                    adapter.setOnVechileClickListner(new VechileCategoryAdapter.onVechileClickListner() {
                        @Override
                        public void onVechileClick(RecyclerView.ViewHolder holder, int postion) {

                            SelectedVechileName = categoryResponse.getData().get(postion).getVehicle_type();

                            Toast.makeText(UserHomePageActivity.this, "Vechile Name : " + SelectedVechileName, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(UserHomePageActivity.this, " unsucessfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VechileCategoryResponse> call, Throwable t) {

                Toast.makeText(UserHomePageActivity.this, "Try Again ", Toast.LENGTH_SHORT).show();
                Toast.makeText(UserHomePageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
