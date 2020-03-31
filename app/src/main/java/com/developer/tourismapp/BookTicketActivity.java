package com.developer.tourismapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BookTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        Intent intent = getIntent();
        String sessionid = intent.getStringExtra("sessionid");
        sessionid = "a9a88c70-5ef7-11ea-8c94-87cc4236f614";
        String destination = intent.getStringExtra("destination");
        destination ="the border";
        Toast.makeText(BookTicketActivity.this, "Session id:"+sessionid+" Destination:"+destination,Toast.LENGTH_LONG).show();
    }
}
