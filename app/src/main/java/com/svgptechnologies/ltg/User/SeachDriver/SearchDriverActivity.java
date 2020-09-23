package com.svgptechnologies.ltg.User.SeachDriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.DriverJson.CallClickCount.CallClickCountResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.Json.UserJson.PostUserCurrentLocation.PostUSerCurrentLocationResponse;
import com.svgptechnologies.ltg.Json.UserJson.SearchDriver.SearchDriverResponse;
import com.svgptechnologies.ltg.Json.UserJson.SendUserLocationToDriver.SendUserLocationToDriverResponse;
import com.svgptechnologies.ltg.Json.UserJson.UserLogin.UserLoginData;
import com.svgptechnologies.ltg.R;
import com.svgptechnologies.ltg.SharedPrefrences.UserSharePrefManager;
import com.svgptechnologies.ltg.User.UserHomePageActivity;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDriverActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnLongClickListener {


    RecyclerView searchDriverRecycle;
    ShimmerFrameLayout searchDriverSimmer;
    SwipeRefreshLayout searchSwipeRefresh;
    SearchDriverRecyclerAdapter adapter;
    Double CurrentLat, CurrentLong;
    boolean is_in_Action_mode = false;
    double latitude, longitude;
    String address;
    String UserNum, DriverId;
    Toolbar searchToolbar;
    ImageView sorrySearchLogo;
    TextView sorySearchTxt, SearchToolbarTextView;
    LatLng latLng = new LatLng(latitude, longitude);
    ImageView SearchDriverLogo, CallSearchDriver, RequestSearchDriver, RequestSentSucessfully, messageSearchDriver;
    String count = "1";

    ArrayList<String> selectedItem = new ArrayList<>();
    int Count = 0;
    String getSelectedDriverNum;
    private static final long UPDATE_IN_MILL = 10000;
    private static final long FAST_IN_MILL = 10000;

    //    location related api
    String[] CheckboxSelectedNumber;

    private FusedLocationProviderClient mfusedLocationProviderClient;
    public SettingsClient msettingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Location currentlocation;
    boolean requwestinglocationupdate = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_driver);


        searchDriverRecycle = findViewById(R.id.searchDriverRecycle);

        searchDriverSimmer = findViewById(R.id.searchDriverSimmer);
        searchDriverRecycle.setLayoutManager(new LinearLayoutManager(this));

        sorySearchTxt = findViewById(R.id.sorySearchTxt);

        SearchToolbarTextView = findViewById(R.id.SearchToolbarTextView);

        sorrySearchLogo = findViewById(R.id.sorrySearchLogo);

        searchSwipeRefresh = findViewById(R.id.searchSwipeRefresh);

        searchToolbar = findViewById(R.id.searchToolbar);
        setSupportActionBar(searchToolbar);


        searchSwipeRefresh.setColorSchemeResources(R.color.colorAccent);

        searchSwipeRefresh.setOnRefreshListener(this);

        //here we are Loading driver list after refreshing
        onLoadingSwipeRefresh();


        searchDriverSimmer.startShimmer();

        searchDriverSimmer.setVisibility(View.VISIBLE);
        searchDriverRecycle.setVisibility(View.GONE);

        sorrySearchLogo.setVisibility(View.GONE);
        sorySearchTxt.setVisibility(View.GONE);

        searchDriver();


        Bundle bundle = getIntent().getExtras();

        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        address = bundle.getString("address");
        //   pincode = bundle.getString("pincode");

// calling and intalizing set location method
        initial();
//        setlocation();


    }

    @Override
    protected void onStart() {
        super.onStart();

//        searchDriverSimmer.startShimmer();
//
//        searchDriverSimmer.setVisibility(View.VISIBLE);
//        searchDriverRecycle.setVisibility(View.GONE);
//
//        sorrySearchLogo.setVisibility(View.GONE);
//        sorySearchTxt.setVisibility(View.GONE);
//
//        searchDriver();
    }

    private void searchDriver() {

        searchSwipeRefresh.setRefreshing(true);

        Bundle bundle = getIntent().getExtras();
        String PinCode = bundle.getString("pincode");
//        String PinCode = "262309";
        final String vechile = bundle.getString("selectedVecgileName");
//        final String vechile = "car";

        if (PinCode != null) {


            LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

            Call<SearchDriverResponse> call = ltgApi.searchDriver(PinCode, vechile, "available");

            call.enqueue(new Callback<SearchDriverResponse>() {
                @Override
                public void onResponse(Call<SearchDriverResponse> call, Response<SearchDriverResponse> response) {

                    final SearchDriverResponse driverResponse = response.body();

                    if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code() && driverResponse.getData() != null) {


                        Toast.makeText(SearchDriverActivity.this, "vichel Name : " + vechile, Toast.LENGTH_SHORT).show();


                        adapter = new SearchDriverRecyclerAdapter(SearchDriverActivity.this, driverResponse.getData());
                        searchDriverRecycle.setAdapter(adapter);

                        searchDriverSimmer.stopShimmer();
                        searchDriverSimmer.setVisibility(View.GONE);

                        searchDriverRecycle.setVisibility(View.VISIBLE);


                        adapter.setOnItemClickListner(new SearchDriverRecyclerAdapter.OnItemClickListner() {


                            @Override
                            public void onCallBtnClicked(View itemview, int position) {

                                CallSearchDriver = itemview.findViewById(R.id.CallSearchDriver);

                                RequestSearchDriver = itemview.findViewById(R.id.RequestSearchDriver);


                                //   Toast.makeText(SearchDriverActivity.this, User_Token, Toast.LENGTH_SHORT).show();


                                // Getting Current Location Latitude and Longtitude
//                                setlocation();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        CallSearchDriver.setVisibility(View.GONE);
                                        RequestSearchDriver.setVisibility(View.VISIBLE);

                                    }
                                }, 7000);


                                String number = driverResponse.getData().get(position).getMobile();
                                DriverId = driverResponse.getData().get(position).getId();

                                // this method to count the Click the Count on Call btn Clicked
                                CallClickCount(DriverId);

                                //Opening DialPad When User Will Click Call btn
                                try {

                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + number));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(getBaseContext(), e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }

                            }

                            // This method is called when user click in send button
                            @Override
                            public void onsendMessageClicked(View itemview, int position) {


                                String number = driverResponse.getData().get(position).getMobile();


                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + number));

                                //      intent.putExtra("sms_body", "remember to buy bread and milk");
                                startActivity(intent);


                                // to send  sms to multiple user
//                            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
//                            i.putExtra("address", "987385438; 750313; 971855;84393");
//                            i.putExtra("sms_body", "Testing you!");
//                            i.setType("vnd.android-dir/mms-sms");
//                            startActivity(i);
                            }


                            @Override
                            public void onSendRequestToDriver(View itemview, int position) {

                                String DriverId = driverResponse.getData().get(position).getId();
                                // this method is for to count the click of Send Request Button
                                SendRequestClickCount(DriverId);

                                // this method is for send user location to databse
                                // and here we are passing driver number to this method

                                postUserLocation(DriverId);
                            }

                            @Override
                            public void onCheckboxSelected(View itemview, int position) {


                                // here if user select the checkbox we have to store n arrayList and
                                // if user unChecked the Checkbox the we have to remove item from array lsit

                                // here we have to check wheateher user select or unSelect hte check box
                                CheckBox selectMultipleDriverCheckbox = itemview.findViewById(R.id.selectMultipleDriverCheckbox);

                                if (selectMultipleDriverCheckbox.isChecked()) {

                                    //if user select the checkBox in that case we have to save the selection to arrayList

                                    //get driver current selected number
                                    getSelectedDriverNum = driverResponse.getData().get(position).getMobile();

                                    // here we are add selected driver number in ArrayList
                                    selectedItem.add(getSelectedDriverNum);

                                    Toast.makeText(SearchDriverActivity.this, selectedItem.toString(), Toast.LENGTH_SHORT).show();

                                    Count = Count + 1;
                                    updateCounter(Count);

                                } else {

                                    // if user unSelect the item from checkbox we have to remove item from array list

                                    // get user unselected number
                                    String unSelectedDriverNum = driverResponse.getData().get(position).getMobile();

                                    //removing unselected number present in selectedItem arrayList
                                    selectedItem.remove(unSelectedDriverNum);

                                    Toast.makeText(SearchDriverActivity.this, selectedItem.toString(), Toast.LENGTH_SHORT).show();

                                    // now we have to update Count
                                    Count = Count - 1;
                                    updateCounter(Count);
                                }

//                                Toast.makeText(SearchDriverActivity.this, CheckboxSelectedNumber.toString(), Toast.LENGTH_SHORT).show();
                            }


                        });

                        searchSwipeRefresh.setRefreshing(false);

                    } else {

                        searchSwipeRefresh.setRefreshing(false);

                        sorrySearchLogo.setVisibility(View.VISIBLE);
                        sorySearchTxt.setVisibility(View.VISIBLE);

                        searchDriverSimmer.setVisibility(View.GONE);
                        //      Toast.makeText(SearchDriverActivity.this, "UnSucessfull", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(SearchDriverActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SearchDriverResponse> call, Throwable t) {


                    searchSwipeRefresh.setRefreshing(false);

                    Toast.makeText(SearchDriverActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });

            // if pincode is null this block will execute
        } else {

            searchSwipeRefresh.setRefreshing(false);

            //here we are showing servise unavialable in ur area
            searchDriverSimmer.setVisibility(View.GONE);

            sorrySearchLogo.setVisibility(View.VISIBLE);
            sorySearchTxt.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onRefresh() {

        searchDriverSimmer.startShimmer();

        searchDriverSimmer.setVisibility(View.VISIBLE);
        searchDriverRecycle.setVisibility(View.GONE);

        sorrySearchLogo.setVisibility(View.GONE);
        sorySearchTxt.setVisibility(View.GONE);

        searchDriver();

        // whenever the user will refresh the value of count will be 0.
        Count = 0;

        // if  user is in selectMultipleDriver Mode and he click refresh
        // the toolbar textView will become ("0 item is Selected") and the arrayList Contain the numer will be empty
        if (is_in_Action_mode == true) {

            // changeing the textView when user Click refresh
            SearchToolbarTextView.setText("0 item is Selected");

            // removing all the number in list when user click refresh
            selectedItem.clear();
        }

        Toast.makeText(this, selectedItem.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onLoadingSwipeRefresh() {

        searchDriverSimmer.startShimmer();

        searchDriverSimmer.setVisibility(View.VISIBLE);
        searchDriverRecycle.setVisibility(View.GONE);

        sorrySearchLogo.setVisibility(View.GONE);
        sorySearchTxt.setVisibility(View.GONE);

        searchDriver();

    }


    @Override
    public void onBackPressed() {


        // if Contextual action mode is enable and back is pressed then Contetual ActionMode is closed
        if (is_in_Action_mode) {

            clearActionMode();
            adapter.notifyDataSetChanged();

        } else {

            // if Contextual action bar is not enable and back is pressed then app will exit
            Intent intent = new Intent(SearchDriverActivity.this, UserHomePageActivity.class);
            startActivity(intent);
            finish();

            super.onBackPressed();
        }

    }


    // Getting Current Location

//    public void setlocation() {
//
//        msettingsClient.checkLocationSettings(locationSettingsRequest)
//                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//                    @Override
//                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//
//                        if (ActivityCompat.checkSelfPermission(SearchDriverActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                             int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
//                        mfusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//
//                        if ( currentlocation != null ) {
//
//                            CurrentLong = currentlocation.getLatitude ( );
//                            CurrentLat = currentlocation.getLongitude ( );
//
//
//                        }
//
//                    }
//                } ).addOnFailureListener ( this, new OnFailureListener ( ) {
//            @Override
//            public void onFailure ( @NonNull Exception e ) {
//
//                int code = (( ApiException ) e).getStatusCode ( );
//
//                switch (code) {
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                        ResolvableApiException eae = ( ResolvableApiException ) e;
//
//                        try {
//                            eae.startResolutionForResult ( SearchDriverActivity.this, code );
//                        } catch (IntentSender.SendIntentException ex) {
//                            ex.printStackTrace ( );
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        //  Toast.makeText(SearchDriverActivity.this, "check your settings", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        } );
//
//    }


    public void initial ( ) {

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient ( SearchDriverActivity.this );

        msettingsClient = LocationServices.getSettingsClient ( this );

        locationCallback = new LocationCallback ( ) {

            @Override
            public void onLocationResult ( LocationResult locationResult ) {
                super.onLocationResult ( locationResult );


                currentlocation = locationResult.getLastLocation ( );
            }
        };

        requwestinglocationupdate = false;

//        creating new location request
        locationRequest = new LocationRequest ( );

//        setting the updating request interval
        locationRequest.setInterval ( UPDATE_IN_MILL );

//        setting the updating request interval
        locationRequest.setFastestInterval ( FAST_IN_MILL );

//        setting the location accuracy
        locationRequest.setPriority ( LocationRequest.PRIORITY_HIGH_ACCURACY );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder ( );

        builder.addLocationRequest ( locationRequest );
        locationSettingsRequest = builder.build ( );


    }


    // this method to save user Current location to Database
    public void postUserLocation ( final String DId ) {

        Bundle bundle = getIntent ( ).getExtras ( );

        double lati = bundle.getDouble ( "latitude" );
        double longi = bundle.getDouble ( "longitude" );
        address = bundle.getString ( "address" );
        String PINCODE = bundle.getString ( "pincode" );

        String lat = String.valueOf ( lati );
        String lang = String.valueOf ( longi );
        String latlang = String.valueOf ( latLng );

        UserLoginData loginData = UserSharePrefManager.getInstance ( SearchDriverActivity.this ).getUserDetail ( );
        String Number = loginData.getMobile ( );

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        Call<PostUSerCurrentLocationResponse> call = ltgApi.postUserLocation ( Number, address, PINCODE, lat, lang, latlang );

        call.enqueue ( new Callback<PostUSerCurrentLocationResponse> ( ) {
            @Override
            public void onResponse ( Call<PostUSerCurrentLocationResponse> call, Response<PostUSerCurrentLocationResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {

                    PostUSerCurrentLocationResponse driverResponse = response.body ( );
//                   String m = driverResponse.getData().getName();

                    SearchDriverRecyclerAdapter driverRecyclerAdapter = adapter;

                    Toast.makeText ( SearchDriverActivity.this, "Sucessfull", Toast.LENGTH_SHORT ).show ( );
                    Toast.makeText ( SearchDriverActivity.this, response.message ( ), Toast.LENGTH_SHORT ).show ( );


                    String DrId = DId;
                    // when user current location will updated in db sucessfully after that sendUserLocationToDrivewr will execute
                    // which will send that location to driver
                    sendUserLocationToDriver ( DrId );

//                    Toast.makeText(SearchDriverActivity.this, "Pincode : "+m, Toast.LENGTH_SHORT).show();
//

                } else {

                    //         Toast.makeText(SearchDriverActivity.this, "UnSucessfull", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure ( Call<PostUSerCurrentLocationResponse> call, Throwable t ) {

                Toast.makeText ( SearchDriverActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( SearchDriverActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


    // this method is to send UserLocationTo Driver
    public void sendUserLocationToDriver ( String driver_Id ) {

        String DrivId = driver_Id;
        Toast.makeText ( this, "this is num : " + driver_Id, Toast.LENGTH_SHORT ).show ( );

        UserLoginData loginData = UserSharePrefManager.getInstance ( SearchDriverActivity.this ).getUserDetail ( );

        UserNum = loginData.getMobile ( );

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        Call<SendUserLocationToDriverResponse> call = ltgApi.sendUserLocationToDriver ( driver_Id, UserNum );

        call.enqueue ( new Callback<SendUserLocationToDriverResponse> ( ) {
            @Override
            public void onResponse ( Call<SendUserLocationToDriverResponse> call, Response<SendUserLocationToDriverResponse> response ) {

                SendUserLocationToDriverResponse driverResponse = response.body ( );
                int status = Integer.valueOf ( driverResponse.getData ( ).getStatus ( ) );

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {


                    Toast.makeText ( SearchDriverActivity.this, "Request Sent Sucessfully", Toast.LENGTH_SHORT ).show ( );
                    Toast.makeText ( SearchDriverActivity.this, response.message ( ), Toast.LENGTH_SHORT ).show ( );

                } else {

                    //     Toast.makeText(SearchDriverActivity.this, status, Toast.LENGTH_SHORT).show();
                    Toast.makeText ( SearchDriverActivity.this, "UnSucessFull", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<SendUserLocationToDriverResponse> call, Throwable t ) {

                Toast.makeText ( SearchDriverActivity.this, "TryAgain", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( SearchDriverActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


    @Override
    public boolean onLongClick ( View v ) {

        // here we are removing the menu from toolbar
        searchToolbar.getMenu ( ).clear ( );

        // add contextual menu in toolbar
        searchToolbar.inflateMenu ( R.menu.search_driver_contextual_menu );

        //    CounterTextView.setVisibility(View.VISIBLE);

        is_in_Action_mode = true;

        adapter.notifyDataSetChanged ( );

        // setting back button on toolbar when Items are long Clicked
        getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( true );
        // changing back button icon in  toolbar
        getSupportActionBar ( ).setHomeAsUpIndicator ( R.drawable.ic_back_btn );

        return true;
    }


    public void updateCounter ( int count ) {

        if ( Count == 0 ) {

            SearchToolbarTextView.setText ( "0 item is Selected" );
        } else {

            SearchToolbarTextView.setText ( Count + " item Selected" );
        }
    }


    @Override
    public boolean onOptionsItemSelected ( @NonNull MenuItem item ) {

        if ( item.getItemId ( ) == R.id.sendSms ) {

            if ( selectedItem.isEmpty ( ) ) {

                Toast.makeText ( this, "Select the number to send Message", Toast.LENGTH_SHORT ).show ( );

            } else {

                // to send  sms to multiple user
                Intent sendNumber = new Intent ( android.content.Intent.ACTION_VIEW );

                // storing number in arrraylist (selectedItem) in string (numb)
                String numb = selectedItem.toString ( );

                // removing starting bracket from the list of number present in numb
                String removeStartingBracket = numb.replace ( "[", "" );

                // removing end bracket from the list of number present in removeStartingBracket
                String removeEndBracket = removeStartingBracket.replace ( "]", "" );

                // replacing comma ( , ) with ( ; ) present in removeEndBracket
                String replacingComma = removeEndBracket.replace ( ",", ";" );

                // Stroing all numer in multiplenumber after replacing Comma
                String multipleNumeber = replacingComma;

                // sending Sms to Multiple Number
                sendNumber.putExtra ( "address", multipleNumeber );
                // i.putExtra("address", "8171831066; 7409740942; 8755420120;84393");
                // i.putExtra("sms_body", "Testing you!");
                sendNumber.setType ( "vnd.android-dir/mms-sms" );

                startActivity ( sendNumber );


                Toast.makeText ( this, selectedItem.toString ( ), Toast.LENGTH_SHORT ).show ( );


                //  clearActionMode();

            }

        } else { // back button on toolbar is pressed

            clearActionMode ( );
            adapter.notifyDataSetChanged ( );
        }

        return true;

    }


    public void clearActionMode ( ) {

        // here first we have to remove the checkbox selectiun
        is_in_Action_mode = false;

        // here we removing menu from toolbarr
        searchToolbar.getMenu ( ).clear ( );

//        // now we have to inflate the normal toolbar
//        toolbar.inflateMenu(R.menu.normal_menu);

        // now we have to remove back button fromtoolbar
        getSupportActionBar ( ).setDisplayHomeAsUpEnabled ( false );

        //now we have to hide TextView From Toolbar
        // CounterTextView.setVisibility(View.GONE);

        // now we have to update the text on text View

        SearchToolbarTextView.setText ( "Near by Service" );

        // we we have to clear the counter variable so next time again it start from 0

        Count = 0;

        // now we have to clear the Selection list
        selectedItem.clear ( );
    }


    // this method is to Count Call button Click
    public void CallClickCount ( String DriverId ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        String driID = DriverId;

        Toast.makeText ( this, driID, Toast.LENGTH_SHORT ).show ( );

        Call<CallClickCountResponse> call = ltgApi.countCallBtnClick ( driID, count );

        call.enqueue ( new Callback<CallClickCountResponse> ( ) {
            @Override
            public void onResponse ( Call<CallClickCountResponse> call, Response<CallClickCountResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {


                    CallClickCountResponse countResponse = response.body ( );

                    String count = String.valueOf ( countResponse.getData ( ).getCount ( ) );

                    Toast.makeText ( SearchDriverActivity.this, "Count : " + count, Toast.LENGTH_SHORT ).show ( );
                    Toast.makeText ( SearchDriverActivity.this, "Sucessfull", Toast.LENGTH_SHORT ).show ( );
                } else {

                    Toast.makeText ( SearchDriverActivity.this, "UnSucessfull", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onFailure ( Call<CallClickCountResponse> call, Throwable t ) {

                Toast.makeText ( SearchDriverActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( SearchDriverActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );


    }


    // this method is to Count Call button Click
    public void SendRequestClickCount ( String driverId ) {

        LTGApi ltgApi = BaseClient.getBaseClient ( ).create ( LTGApi.class );

        String id = driverId;

        String counter = "1";
        Toast.makeText ( this, id, Toast.LENGTH_SHORT ).show ( );

        Call<CallClickCountResponse> call = ltgApi.countSendRequestBtnClick ( id, counter );

        call.enqueue ( new Callback<CallClickCountResponse> ( ) {
            @Override
            public void onResponse ( Call<CallClickCountResponse> call, Response<CallClickCountResponse> response ) {

                if ( response.isSuccessful ( ) && HttpURLConnection.HTTP_OK == response.code ( ) ) {


                    CallClickCountResponse countResponse = response.body ( );

                    String counts = String.valueOf ( countResponse.getData ( ).getCount ( ) );

                    Toast.makeText ( SearchDriverActivity.this, "Count : " + counts, Toast.LENGTH_SHORT ).show ( );
                    Toast.makeText ( SearchDriverActivity.this, "Sucessfull", Toast.LENGTH_SHORT ).show ( );
                } else {

                    Toast.makeText ( SearchDriverActivity.this, "UnSucessfull", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override

            public void onFailure ( Call<CallClickCountResponse> call, Throwable t ) {

                Toast.makeText ( SearchDriverActivity.this, "Try Again", Toast.LENGTH_SHORT ).show ( );
                Toast.makeText ( SearchDriverActivity.this, t.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
            }
        } );


    }

}


