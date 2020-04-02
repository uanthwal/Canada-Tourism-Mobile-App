package com.developer.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BookingConfirmed extends AppCompatActivity {

    Button btnHome;
    Button btnSendPDF;
    private static final String TAG = "BookingConfirmed";
    String travel_name, travel_mode, mode_number, price_travel, source, destination, travel_date, Booking_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmed);
        Intent intent = getIntent();
        travel_name = intent.getStringExtra("TRAVEL_NAME");
        travel_mode = intent.getStringExtra("TRAVEL_MODE");
        mode_number = intent.getStringExtra("MODE_NUMBER");
        price_travel = intent.getStringExtra("PRICE_TRAVEL");
        source = intent.getStringExtra("SOURCE");
        destination = intent.getStringExtra("DESTINATION");
        travel_date = intent.getStringExtra("TRAVEL_DATE");
        Booking_id = intent.getStringExtra("BOOKING_ID");

        TextView booking_id = (TextView) findViewById(R.id.bookingid);
        booking_id.setText(Booking_id);
        TextView passenger = (TextView) findViewById(R.id.passenger);
        passenger.setText(AppGlobalVars.USER_NAME);
        TextView Source = (TextView) findViewById(R.id.Source);
        Source.setText(source);
        TextView dest = (TextView) findViewById(R.id.destination);
        dest.setText(destination);
        TextView travelDate = (TextView) findViewById(R.id.traveDate);
        travelDate.setText(travel_date);
        TextView fare = (TextView) findViewById(R.id.fare);
        fare.setText(price_travel);

        btnHome = findViewById(R.id.home);
        btnSendPDF = findViewById(R.id.sendpdf);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookingConfirmed.this, SearchPlaces.class);
                startActivity(i);
            }
        });

        btnSendPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPDFToMail();
            }
        });

    }

    private void sendPDFToMail() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("booking_id", Booking_id);
        String url = AppConstants.SEND_PDF_URL;
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                String rc="", message="";
                try {
                    rc=response.getString("code");
                    message = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (rc.equals("200"))
                {
                    Toast.makeText(BookingConfirmed.this, "Ticket PDF has been sent to your registered mailid!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+ "PDF Sending Success.");

                }
                else{
                    Log.d(TAG, "onResponse: Server failure "+ message);
                    Toast.makeText(BookingConfirmed.this, "Sending PDF Failure: "+message, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.e(TAG, "errorResponse:" , error);
                Toast.makeText(BookingConfirmed.this, "Sending PDF Failed! Try again.", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);

    }
}
