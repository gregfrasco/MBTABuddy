package com.mobileappclass.lab4.googlemapsapiproto;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        //Get bitmap descriptor for the icon image resource
        BitmapDescriptor markIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_cookie);

        //Create a LatLng container for the location for the icon
        //Maybe we can get from MBTA API?
        LatLng winthrop = new LatLng(42.3804809,-70.9827367);

        //Add the marker to map
        Marker winthropMark = mMap.addMarker(new MarkerOptions()
                .position(winthrop)
                .title("Winthrop Marker")
                .icon(markIcon));

        //Procedure to move camera to a position
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(winthrop, 17));

        //Can dynamically change marker position
        LatLng newLoc = new LatLng(42.3804809, -70.9627367);
        winthropMark.setPosition(newLoc);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 13));

        //All marker "clicks" come here, each marker doesn't get its own listener
        //So need to id them here
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                return false;
            }
        });
    }

    JSONObject getGMapsRouteObj(String origin, String dest)
    {
        return getGMapsRouteObj(origin, dest, null);
    }

    JSONObject getGMapsRouteObj(String origin, String dest, String travelType)
    {
        String gMapsApiReq = "http://maps.googleapis.com/maps/api/directions/";
        String requestType = "json";

        //Parameters to be used in requesting directions from GMaps web API
        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put("origin", origin);
        params.put("destination", dest);
        if(travelType != null)
        {
            params.put("mode", travelType);
        }
        params.put("key", getString(R.string.google_maps_key));//API key

        //Build our full request string
        String fullRequest = gMapsApiReq;
        fullRequest += requestType + "?";
        for(String paramKey : params.keySet())
        {
            String value = params.get(paramKey);
            fullRequest += paramKey + "=" + value;
        }

        return null;
    }
}
