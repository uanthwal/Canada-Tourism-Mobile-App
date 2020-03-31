package com.developer.tourismapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class SearchPlaces extends AppCompatActivity {
    ArrayList<Integer> imageList;
    RecyclerView recyclerView;
//    RecyclerViewAdapter recyclerViewAdapter;
    Button btnSearch;
    EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);
        etInput = findViewById(R.id.et_input);
        btnSearch = findViewById(R.id.btn_search);
//        recyclerView= findViewById(R.id.rv_places);
//        recyclerViewAdapter = new RecyclerViewAdapter(this,  imageList);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        recyclerView.setAdapter(recyclerViewAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputtext = etInput.getText().toString();
                if (!inputtext.equals(""))
                {
                    Intent intent = new Intent(SearchPlaces.this, SearchResults.class);
                    startActivity(intent);
                }
            }
        });
    }
}
