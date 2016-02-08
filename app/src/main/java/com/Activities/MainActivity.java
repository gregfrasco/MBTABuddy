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
import mbta.mbtabuddy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBTA mbta = MBTA.getInstance();
        List<ParentStation> station = mbta.getStopsByRoute(mbta.getCompatibleRoutes(MBTARoutes.Routes.Blue_Line));
        List<Route> stations = mbta.getRoutesByStop(station.get(0).getOutbound());
        for(Route route : stations){
            Log.v("MBTA",route.getRouteName());
        }
    }
}
