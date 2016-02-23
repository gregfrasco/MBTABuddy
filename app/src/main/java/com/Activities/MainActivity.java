package com.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import mbta.Line;
import mbta.Lines;
import mbta.MBTA;
import mbta.mbtabuddy.R;

public class MainActivity extends AppCompatActivity {

    //TODO Change MBTARoutes to route

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBTA mbta = MBTA.getInstance();
        Line redline = mbta.getLine(Lines.Red_Line);
        Log.v("MBTA",redline.getStations().get(0).getArrivalTimes()[0]);
        //Test Code
        //Intent trackerInten = new Intent(MainActivity.this, TrackerActivity.class);
        //startActivity(trackerInten);
    }
}
