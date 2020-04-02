package com.developer.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardDetails extends AppCompatActivity {
    String booking_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        CardForm cardForm = (CardForm) findViewById(R.id.cardform);
        TextView txtDes = (TextView) findViewById(R.id.payment_amount);
        Button btnpay = (Button) findViewById(R.id.btn_pay);
        final Intent intent =getIntent();
        final String travel_name = intent.getStringExtra("TRAVEL_NAME");
        final String travel_mode = intent.getStringExtra("TRAVEL_MODE");
        final String mode_number = intent.getStringExtra("MODE_NUMBER");
        final String price_travel = intent.getStringExtra("PRICE_TRAVEL");
        final String source = AppGlobalVars.SOURCE;
        final String destination = AppGlobalVars.SEARCH_PLACE_ID;
        final String travel_date =AppGlobalVars.TRAVEL_DATE;

        String tPrice = "$"+price_travel;
        txtDes.setText(tPrice);
        btnpay.setText(String.format("Make Payment %s",txtDes.getText()));


        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://cloud-5409.herokuapp.com/api/book-ticket";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            booking_id = jsonObject.getString("booking_id");

                                //myModel m = new myModel(jo.getString("mode_company"), jo.getString("mode"), jo.getString("mode_number"),jo.getString("mode_fare"));
                                //mList.add(m);
                                //booking_id = jo.getString("booking_id");




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                        Log.d("Error.Response", "Errorrrrrrrrrrrrrrrrrrrrrrrr");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("src", AppGlobalVars.SOURCE);
                params.put("dest", AppGlobalVars.SEARCH_PLACE_ID);

                return params;
            }
        };
        queue.add(postRequest);

        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {

                Intent i = new Intent(CardDetails.this, BookingConfirmed.class);
                i.putExtra("TRAVEL_NAME", travel_name);
                i.putExtra("TRAVEL_MODE", travel_mode);
                i.putExtra("MODE_NUMBER", mode_number);
                i.putExtra("PRICE_TRAVEL", price_travel);
                i.putExtra("SOURCE",source);
                i.putExtra("DESTINATION", destination);
                i.putExtra("TRAVEL_DATE",travel_date);
                i.putExtra("BOOKING_ID", booking_id);
                startActivity(i);
            }
        });

    }

}
