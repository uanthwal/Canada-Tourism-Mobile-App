package com.developer.tourismapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 80;
    ProgressBar progressBar;
    private int i = 0;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressbar_splash);
        progressBar.setProgress(0);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i < SPLASH_TIME_OUT) {
                    progressBar.setProgress(i);
                    i += 1.5;
                } else {
                    timer.cancel();
                    finish();
                    startActivity(new Intent(SplashActivity.this, SearchPlaces.class));
                }
            }
        }, 0, SPLASH_TIME_OUT);
    }
}
