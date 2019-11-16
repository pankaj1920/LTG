package com.svgptechnologies.ltg.Driver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;

import com.svgptechnologies.ltg.R;

public class CancleBookingReasonActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar canclebookingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancle_booking_reason);

        canclebookingToolbar = findViewById(R.id.canclebookingToolbar);

        setSupportActionBar(canclebookingToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }
}
