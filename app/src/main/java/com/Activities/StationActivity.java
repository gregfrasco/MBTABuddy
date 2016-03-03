package com.Activities;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import directions.AbstractRouting;
import directions.Route;
import directions.RouteException;
import directions.Routing;
import directions.RoutingListener;
import DataManagement.DataStorageManager;
import gmap.MapManager;
import gmap.StationMarker;
import gmap.TrainMarker;
import mbta.Line;
import mbta.Lines;
import mbta.MBTA;
import mbta.Station;
import mbta.mbtaAPI.Vehicle;
import mbta.mbtabuddy.R;

public class StationActivity extends FragmentActivity implements OnMapReadyCallback {

    private DataStorageManager dataManager;
    private MapManager mapManager;
    private Station station;
    private List<TrainMarker> trainMarkers = new ArrayList<TrainMarker>();
    private List<StationMarker> stationMarkers = new ArrayList<StationMarker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.stationMap);
        mapFragment.getMapAsync(this);

        //Get our id of the station from the intent
        Bundle bundle = getIntent().getExtras();
        String stationID = bundle.getString("ID");
        this.station = new Station(stationID);
        setTitle(station.getStationName());
        this.mapManager = new MapManager(this);
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
        this.mapManager.setMap(googleMap);
        new LoadStation(StationActivity.this,this.station,this.mapManager).execute();
    }

    public void addFavoriteStation(View view) {
        if(dataManager == null)
            dataManager = DataStorageManager.getInstance();
        dataManager.SetContext(this); //TODO: Move to onResume()? or other method?

        //Save it
        dataManager.SaveStationFavorite(station.getStationID(), station.getStationName());
    }

    /**
     * Class to run the login process in the background while running the
     * loading screen in the main thread
     */
    private class LoadStation extends AsyncTask<String, Integer, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(StationActivity.this);
        private StationActivity activity;
        private MapManager mapManager;
        private Station station;

        public LoadStation(StationActivity activity,Station station,MapManager mapManager){
            this.activity = activity;
            this.mapManager = mapManager;
            this.station = station;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Loading...");
            this.dialog.setIndeterminate(true);
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                this.mapManager.drawTrainLines(station.getLines());
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(this.dialog.isShowing()){
                this.dialog.dismiss();
            }
            this.mapManager.drawAdjustedStations(station.getLine());
            this.mapManager.addTrains(station.getLines(),station);
            this.mapManager.zoomToStationMarker(station.getStationID(),16);
        }
    }

}

