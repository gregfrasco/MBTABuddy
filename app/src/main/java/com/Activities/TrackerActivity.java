package com.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gmap.MapManager;
import gmapdirections.GDirections;
import mbta.MBTARoutes;
import mbta.mbtabuddy.R;

public class TrackerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GDirections gDirections;
    private MapManager mapManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Get our GDirections instance, give it context
        gDirections = GDirections.getInstance();
        gDirections.setContext(getBaseContext());
        //gDirections.Test();

        //Get our mapManager singleton and give it the context
        mapManager = MapManager.getInstance();
        mapManager.SetContext(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapManager.SetMap(mMap);

        //Test Code
        mapManager.AddTrainMarker("1234", new LatLng(42.3394899, -71.087803), "Test Train", MBTARoutes.Routes.Blue_Line);
        mapManager.ZoomToTrainMarker("1234", 16);

        mapManager.AddStationMarker("Ruggles", new LatLng(42.339486, -71.085609));
        //End Test
    }
}
