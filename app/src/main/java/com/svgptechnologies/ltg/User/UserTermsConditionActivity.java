package com.svgptechnologies.ltg.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.webkit.WebView;

import com.svgptechnologies.ltg.R;

public class UserTermsConditionActivity extends AppCompatActivity {

    WebView webView;
    Toolbar termsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_terms_condition);


        termsToolbar = (Toolbar) findViewById(R.id.termsToolbar);
        setSupportActionBar(termsToolbar);

        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/terms.html");

        webView.getSettings().setJavaScriptEnabled(true);
        setTitle("Terms and Condition");
    }
}
