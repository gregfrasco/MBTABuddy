package com.Activities;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gmap.MapManager;
import gmapdirections.GDirections;
import gmapdirections.GPSManager;
import gmapdirections.RouteInfoContainer;
import mbta.Line;
import mbta.Lines;
import mbta.MBTA;
import mbta.Station;
import mbta.mbtabuddy.R;

public class TrackerActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private GDirections gDirections;
    private MapManager mapManager;
    private GPSManager gpsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Get our GDirections instance, give it context
        gDirections = GDirections.getInstance();
        gDirections.setContext(getBaseContext());
        gDirections.Test();

        //Get our mapManager singleton and give it the context
        mapManager = MapManager.getInstance();
        mapManager.SetContext(this);

        //Set up gpsManager with context
        gpsManager = GPSManager.getInstance();
        gpsManager.InitLocationManager(this);
    }

    private void setUpDestinationInfo(LatLng destination)
    {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(PermissionConstants.getEnum(requestCode)) {
            case PERMISSION_APPROVED:
                break;
            default:
                break;
        }
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
        MBTA mbta = MBTA.getInstance();
        mapManager.drawLine(new Line(Lines.Orange_Line));
        mapManager.drawLine(new Line(Lines.Red_Line));
        mapManager.drawLine(new Line(Lines.Blue_Line));
        mapManager.drawLine(new Line(Lines.Green_Line_B));
        mapManager.drawLine(new Line(Lines.Green_Line_C));
        mapManager.drawLine(new Line(Lines.Green_Line_D));
        mapManager.drawLine(new Line(Lines.Green_Line_E));
        mapManager.drawLine(new Line(Lines.Mattapan_Line));

        //End Test
    }
}

