package com.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataManagement.LoadingDialogManager;
import gmap.MapManager;
import gmapdirections.GDirections;
import gmapdirections.GPSManager;
import mbta.Line;
import mbta.Lines;
import mbta.Station;
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

        Button searchButton = (Button) retView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the search string
                EditText searchBox = (EditText) getActivity().findViewById(R.id.destSearch);
                String searchString = searchBox.getText().toString();

                //Send it to search activity
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                searchIntent.putExtra("searchString", searchString);
                startActivity(searchIntent);
            }
        });

        //Build station list
        createStationList(retView);
        return retView;
    }

    private void createStationList(View view)
    {
        //Create our list of lines for each lines enum
        List<ByLineListContainer> lineItems = new ArrayList<>();
        final List<Line> lineVals = Lines.getInstance().values();
        for(Line line : lineVals)
        {
            ByLineListContainer newLineItem = new ByLineListContainer();
            newLineItem.lineColor = line;
            newLineItem.lineName = line.getLineName();
            lineItems.add(newLineItem);
        }
        //Create adapter
        ByListListAdapter adapter = new ByListListAdapter(getActivity(), 0, lineItems);
        GridView lineList = (GridView) view.findViewById(R.id.byLineList);

        //Set adapter
        lineList.setAdapter(adapter);
    }

    public void enableLocationManager() {
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
        //Get our mapManager singleton and give it the context
        mapManager = new MapManager(getActivity(),mMap);
        /*mapManager.drawAllTrainLines();
        mapManager.setMap(mMap);*/

        //Set up gpsManager with context
        gpsManager = GPSManager.getInstance();
        //Get the location manager service
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        //Get the permissions for the location service if needed
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PermissionConstants.LOCATION_TrackerFragment.getValue());
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsManager);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsManager);
            gpsManager.InitLocationManager(getActivity(), locationManager);
            Log.v("Tracker", "No Permissions Required, hooked up gpsManager");
        }
      //  mapManager.drawAllStations();
        new LoadMapLines(mMap, mapManager).execute();

    }

    class LoadMapLines extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog pd;
        private GoogleMap mMap;
        private MapManager mManager;
        LoadMapLines(GoogleMap map, MapManager manager)
        {
            mMap = map;
            mManager = manager;

        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            LoadingDialogManager.getInstance().SetLoadingMessage("Loading Map...");
            LoadingDialogManager.getInstance().ShowLoading(getActivity());
        }

        @Override
        protected Void doInBackground(Void... params) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mManager.setMap(mMap);
                    mManager.drawAllTrainLines();
                    mManager.drawAllStations();
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Station station = Lines.getInstance().GreenLineE.getStations().get(0);
            mapManager.zoomToStationMarker(station.getStationID(),18);
            LoadingDialogManager.getInstance().DismissLoading();
        }
    }

}

