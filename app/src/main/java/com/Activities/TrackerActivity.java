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

        gDirections = GDirections.getInstance();
        gDirections.setContext(getBaseContext());
        gDirections.Test();

        //Get our mapManager singleton and give it the layout inflater
        mapManager = MapManager.getInstance();
        mapManager.SetLayoutInflater(getLayoutInflater());
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
        mapManager.AddTrainMarker("testTrain", new LatLng(-42, 70), "Test Train", MBTARoutes.Routes.Blue_Line);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-42, 70), 12));
        //End Test
    }
}
