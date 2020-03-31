package com.developer.tourismapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchResults extends AppCompatActivity {

    Button book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        book=findViewById(R.id.btn_book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResults.this, BookTicketActivity.class);
                intent.putExtra("sessionid", "a9a88c70-5ef7-11ea-8c94-87cc4236f614");
                intent.putExtra("destination", "the border");
                startActivity(intent);

            }
        });
    }
}
