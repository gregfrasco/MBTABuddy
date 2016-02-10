package com.Activities;

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
        List<Trip> trips = mbta.getScheduleByTrip(mbta.getScheduleByRoute(mbta.RED_LINE).get(0));
        for(Trip trip : trips){
            Log.v("MBTA", trip.getTripName() + " " + trip.getSchArrDt() + " " + trip.getSchDepDt());
        }
    }
}
