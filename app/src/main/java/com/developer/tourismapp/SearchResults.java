package com.developer.tourismapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResults extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button book;
    ArrayList<PlacesTO> searchResultPlaces = new ArrayList<PlacesTO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Intent intent = getIntent();

        recyclerView = findViewById(R.id.search_results_rv);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ResultPlaceListAdapter(getApplicationContext(), searchResultPlaces, new ResultPlaceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        getSearchResults();
    }

    private void getSearchResults() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("search_text", AppGlobalVars.SEARCH_TEXT);
        params.put("session_id", AppGlobalVars.SESSION_ID);
        String url = AppConstants.SEARCH_URL;
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (null != jsonArray && jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PlacesTO placesTO = new PlacesTO();
                            placesTO.setPlaceName(jsonObject.get("place_name").toString());
                            placesTO.setPlaceId(jsonObject.get("place_id").toString());
                            placesTO.setPlaceDesc(jsonObject.get("desc").toString());
                            placesTO.setProvinceId(jsonObject.get("province_id").toString());
                            placesTO.setImgURL(AppConstants.S3_BASE_URL + jsonObject.get("place_name").toString() + ".jpg");
                            searchResultPlaces.add(placesTO);
                        }
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
