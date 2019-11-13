package com.svgptechnologies.ltg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.svgptechnologies.ltg.Json.BaseClient;
import com.svgptechnologies.ltg.Json.CustomerCare.CustomerCareResponse;
import com.svgptechnologies.ltg.Json.LTGApi;
import com.svgptechnologies.ltg.User.UserHomePageActivity;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerCareActivity extends AppCompatActivity {

    Button ContactSubmitBtn, feedBackOkBtn;
    EditText ContactName, ContatUsNumber, ContactUsEmail, ContactUsMessage;
    Spinner UserSelectLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care);


        ContactSubmitBtn = (Button) findViewById(R.id.ContactSubmitBtn);

        ContactName = (EditText) findViewById(R.id.ContactName);

        ContatUsNumber = (EditText) findViewById(R.id.ContatUsNumber);

        ContactUsEmail = (EditText) findViewById(R.id.ContactUsEmail);

        ContactUsMessage = (EditText) findViewById(R.id.ContactUsMessage);

        UserSelectLanguage = (Spinner) findViewById(R.id.UserSelectLanguage);

        //storing image name in String Array
        String[] selectLanguage = {"Select Language", "English", "Hindi", "Kannada", "Tamil", "Telugu"};

        //Create Array Adapter Object
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CustomerCareActivity.this,
                android.R.layout.simple_list_item_1, selectLanguage);

        //setting Adapter in spinner
        UserSelectLanguage.setAdapter(adapter);


        ContactSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendCustomerCareRequest();
                //     showCustomDialog();
            }
        });

    }

    //Creating Custon Dialog FoR I have a referral code in Navigation Drawer
    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_feedback_sent_dialog_box, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        //add this line to make your dialogbox radius round
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();


        feedBackOkBtn = alertDialog.findViewById(R.id.feedBackOkBtn);
        feedBackOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerCareActivity.this, UserHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void sendCustomerCareRequest() {

        String language = UserSelectLanguage.getSelectedItem().toString();
        String name = ContactName.getText().toString();
        String number = ContatUsNumber.getText().toString();
        String email = ContactUsEmail.getText().toString();
        String message = ContactUsMessage.getText().toString();

        if (language.equalsIgnoreCase("Select Language")) {
            Toast.makeText(this, language, Toast.LENGTH_LONG).show();
            return;
        }

        if (name.equals("")) {

            ContactName.setError("Enter Name");
            ContactName.requestFocus();
            return;
        }
        if (number.equals("")) {

            ContatUsNumber.setError("Enter Number");
            ContatUsNumber.requestFocus();
            return;
        }
        if (message.equals("")) {

            ContactUsMessage.setError("Enter Message");
            ContactUsMessage.requestFocus();
            return;
        }

        LTGApi ltgApi = BaseClient.getBaseClient().create(LTGApi.class);

        Call<CustomerCareResponse> call = ltgApi.getCustomerRequest(name, language, email, number, message);

        call.enqueue(new Callback<CustomerCareResponse>() {
            @Override
            public void onResponse(Call<CustomerCareResponse> call, Response<CustomerCareResponse> response) {

                CustomerCareResponse careResponse = response.body();
                String s = response.message();
                if (response.isSuccessful() && HttpURLConnection.HTTP_OK == response.code()) {


                    String status = careResponse.getData();

                    Toast.makeText(CustomerCareActivity.this, status, Toast.LENGTH_LONG).show();

                    showCustomDialog();

                } else {
                    Toast.makeText(CustomerCareActivity.this, s, Toast.LENGTH_SHORT).show();
                    Toast.makeText(CustomerCareActivity.this, "UnSucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerCareResponse> call, Throwable t) {

                Toast.makeText(CustomerCareActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerCareActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
