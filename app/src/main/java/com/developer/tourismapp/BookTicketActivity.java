package com.developer.tourismapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class BookTicketActivity extends AppCompatActivity {

    public TextView mDisplayDate;
    public DatePickerDialog.OnDateSetListener mDateSetListener;
    public Spinner mySpinner;
    public TextView Destination;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        Intent intent = getIntent();
        String sessionid = AppGlobalVars.SESSION_ID;

        mySpinner = (Spinner) findViewById(R.id.spinner);
        Destination = (TextView) findViewById(R.id.dest);
        Destination.setText(AppGlobalVars.SEARCH_PLACE_ID);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BookTicketActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sources));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        Button btn = (Button) findViewById(R.id.bookTicketNow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTravelModes();
            }
        });

        mDisplayDate = (TextView) findViewById(R.id.pickDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        BookTicketActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet : mm/dd/yyyy : "+ month + "/" + day + "/"+ year );
                date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }

    public void openTravelModes() {
        String source = mySpinner.getSelectedItem().toString();
        String destination = Destination.getText().toString();
        Intent intent = new Intent(BookTicketActivity.this, TravelMode.class);
        AppGlobalVars.SOURCE = source;
//        intent.putExtra("SOURCE", source);
//        intent.putExtra( "DESTINATION", AppGlobalVars.SEARCH_PLACE_ID);
//        intent.putExtra("DESTINATION","AULA");
//        intent.putExtra("TRAVELDATE", date);
        AppGlobalVars.TRAVEL_DATE = date;
        startActivity(intent);
    }

}
