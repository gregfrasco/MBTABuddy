package com.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import gmap.MapManager;
import gmapdirections.GDirections;
import gmapdirections.GPSManager;
import mbta.Lines;
import mbta.mbtabuddy.R;

public class TrackerFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GDirections gDirections;
    private MapManager mapManager;
    private GPSManager gpsManager;
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancetate) {

        Log.v("Tracker", "View Created");
        View retView = inflater.inflate(R.layout.activity_tracker, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Get our GDirections instance, give it context
        gDirections = GDirections.getInstance();
        gDirections.setContext(getActivity());
        gDirections.Test();

        //Get our mapManager singleton and give it the context
        mapManager = MapManager.getInstance();
        mapManager.SetContext(getActivity());

        return retView;
    }


    public void enableLocationManager()
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PermissionConstants.LOCATION_TrackerFragment.getValue());
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsManager);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsManager);
        gpsManager.InitLocationManager(getActivity(), locationManager);
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
        //Set up gpsManager with context
        gpsManager = GPSManager.getInstance();

        //Get the location manager service
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        //Get the permissions for the location service if needed
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PermissionConstants.LOCATION_TrackerFragment.getValue());
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsManager);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsManager);
            gpsManager.InitLocationManager(getActivity(), locationManager);
            Log.v("Tracker", "No Permissions Required, hooked up gpsManager");
        }

        //Test Code
        mapManager.AddTrainMarker("1234", new LatLng(42.3394899, -71.087803), "Test Train", Lines.Blue_Line);
        mapManager.ZoomToTrainMarker("1234", 16);

        mapManager.AddStationMarker("Ruggles", new LatLng(42.339486, -71.085609));
        //End Test
    }
}

