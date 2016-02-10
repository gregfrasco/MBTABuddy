package com.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import mbta.MBTA;
import mbta.MBTARoutes;
import mbta.ParentStation;
import mbta.Route;
import mbta.Station;
import mbta.Trip;
import mbta.mbtabuddy.R;

public class MainActivity extends AppCompatActivity {

    //TODO Change MBTARoutes to route

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBTA mbta = MBTA.getInstance();
        List<Trip> trips = mbta.getScheduleByStop(mbta.getStopsByRoute(mbta.getRoutes().get(0)).get(0).getOutbound());
        for(Trip trip: trips){
            Log.v("MBTA",trip.getTripName());
        }

        //Test Code
        Intent trackerInten = new Intent(MainActivity.this, TrackerActivity.class);
        startActivity(trackerInten);
    }
}
