package com.svgptechnologies.ltg.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.svgptechnologies.ltg.Driver.DriverRegistration.DriverRigesterActivity;
import com.svgptechnologies.ltg.R;

public class SelectPaymentActivity extends AppCompatActivity {

    Button paymentProcedeBtn;
    CardView OneYearCaredView, NineMonthCaredView, SixMonthCaredView, ThreeMonthCaredView;
    TextView threeMonthPrice, SixMonthPrice, NineMonthPrice, OneYearPrice;
    RadioButton threeMonthRadio, SixMonthRadio, NineMonthRadio, OneYearRadio;

    String ServiceName;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_select_payment );

        paymentProcedeBtn = findViewById ( R.id.paymentProcedeBtn );

        OneYearCaredView = findViewById ( R.id.OneYearCaredView );

        NineMonthCaredView = findViewById ( R.id.NineMonthCaredView );

        SixMonthCaredView = findViewById ( R.id.SixMonthCaredView );

        ThreeMonthCaredView = findViewById ( R.id.ThreeMonthCaredView );

        threeMonthPrice = findViewById ( R.id.threeMonthPrice );

        SixMonthPrice = findViewById ( R.id.SixMonthPrice );

        NineMonthPrice = findViewById ( R.id.NineMonthPrice );

        OneYearPrice = findViewById ( R.id.OneYearPrice );

        threeMonthRadio = findViewById ( R.id.threeMonthRadio );

        SixMonthRadio = findViewById ( R.id.SixMonthRadio );

        NineMonthRadio = findViewById ( R.id.NineMonthRadio );

        OneYearRadio = findViewById ( R.id.OneYearRadio );


        Bundle bundle = getIntent ().getExtras ();
        ServiceName = bundle.getString ( "service_name" );

        paymentProcedeBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                Intent intent = new Intent ( SelectPaymentActivity.this, DriverRigesterActivity.class );
                Bundle bundle = new Bundle ( );
                bundle.putString ( "service_name", ServiceName );
                intent.putExtras ( bundle );

                startActivity ( intent );
                finish ( );
            }
        } );

        ThreeMonthCaredView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                threeMonthRadio.setChecked ( true );
                SixMonthRadio.setChecked ( false );
                NineMonthRadio.setChecked ( false );
                OneYearRadio.setChecked ( false );

            }
        } );

        SixMonthCaredView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                threeMonthRadio.setChecked ( false );
                SixMonthRadio.setChecked ( true );
                NineMonthRadio.setChecked ( false );
                OneYearRadio.setChecked ( false );
            }
        } );

        NineMonthCaredView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                threeMonthRadio.setChecked ( false );
                SixMonthRadio.setChecked ( false );
                NineMonthRadio.setChecked ( true );
                OneYearRadio.setChecked ( false );

            }
        } );

        OneYearCaredView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {

                threeMonthRadio.setChecked ( false );
                SixMonthRadio.setChecked ( false );
                NineMonthRadio.setChecked ( false );
                OneYearRadio.setChecked ( true );
            }
        } );


    }
}
