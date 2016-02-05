package com.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import mbta.MBTA;
import mbta.ParentStation;
import mbta.Station;
import mbta.mbtabuddy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBTA mbta = MBTA.getInstance();
        List<ParentStation> stationList = mbta.getStopsByRoute(mbta.getRoutes().get(1));
        for(ParentStation station : stationList){
            Log.v("MBTA",station.getName());
        }
    }
}
