package com.developer.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TravelMode extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<myModel> mList = new ArrayList<myModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_mode);
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = AppConstants.GET_TRAVEL_MODES;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                myModel m = new myModel(jo.getString("mode_company"), jo.getString("mode"), jo.getString("mode_number"), jo.getString("mode_fare"), jo.getString("mode_company"), jo.getString("mode_id"));
                                mList.add(m);
                            }
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                        Log.d("Error.Response", "Errorrrrrrrrrrrrrrrrrrrrrrrr");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("src", AppGlobalVars.SOURCE);
                params.put("dest", AppGlobalVars.SEARCH_PLACE_ID);
                return params;
            }
        };
        queue.add(postRequest);
        recyclerView = findViewById(R.id.myrecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecyclerAdapter(mList, this, AppGlobalVars.SOURCE, AppGlobalVars.SEARCH_PLACE_ID, AppGlobalVars.TRAVEL_DATE);
    }
}
