package com.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mbta.MBTA;
import mbta.mbtabuddy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBTA mbta = MBTA.getInstance();
        mbta.getRoutes();
    }
}
