package com.Activities;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

public class StationActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener {

    private DataStorageManager dataManager;
    private GoogleMap map;
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
        map = googleMap;
        this.drawTrainLines(this.station.getLines());
        this.addTrains(this.station.getLines(), station);
        this.drawStations(this.station.getLines());
        this.zoomToStationMarker(station.getStationID(), 17);
    }

    private void addTrains(List<Line> lines, Station station) {
        MBTA mbta = MBTA.getInstance();
        for(Line line: lines) {
            for (Vehicle vehicle : MBTA.getInstance().getVehiclesByRoute(line)) {
                this.addTrainMarker(vehicle.getVehicleId(), vehicle.getLatLng(), "TESTING",line.getLines());
            }
        }
    }

    public void drawStations(List<Line> lines) {
        for(Line line: lines){
            line.adjustStations();
            for(Station station : line.getStations()){
                this.addStationMarker(station.getStationID(), station.getLatLan());
            }
        }
    }

    public void drawTrainLines(List<Line> lines) {
        for(Line line:lines){
            this.drawLine(line);
        }
    }

    public void drawLine(Line line) {
        if(line.getMapPoints()== null) {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.TRANSIT)
                    .waypoints(line.getTerminalStation1().getLatLan(), line.getTerminalStation2().getLatLan())
                    .key("AIzaSyAuq6B6ktEChZCEfB-LbwyxshF44bWKItM")
                    .withListener(this)
                    .withLine(line)
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.v("MBTA", "ROUTE FAILED");
    }

    @Override
    public void onRoutingStart() {
        Log.v("MBTA", "ROUTE START");
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex, Line line) {
        route.get(0).getPoints().remove(0);
        route.get(0).getPoints().remove(route.get(0).getPoints().size() - 1);
        line.setMapPoints(route.get(0).getPoints());
        line.drawLine(this.map);
    }

    @Override
    public void onRoutingCancelled() {
        Log.v("MBTA", "ROUTE Cancelled");
    }

    public void addStationMarker(String stationName, LatLng location) {
        Marker newMarker = map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(stationName)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_station))
        );
        StationMarker newsm = new StationMarker(stationName, newMarker);
        stationMarkers.add(newsm);
    }

    public void zoomToStationMarker(String stationName, int zoomNum) {
        StationMarker sm = getStationMarker(stationName);
        LatLng pos = sm.getMarker().getPosition();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoomNum));
    }

    public StationMarker getStationMarker(String stationName) {
        for (StationMarker stat : stationMarkers) {
            if (stat.getStationName().equals(stationName))
                return stat;
        }

        return null;
    }

    public void zoomToTrainMarker(String vehicleNum, int zoomNum) {
        TrainMarker train = getTrainMarkerFromVehicleNum(vehicleNum);
        LatLng position = train.GetMarker().getPosition();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomNum));
    }

    //Add a train to the map
    public void addTrainMarker(String vehicleNum, LatLng location, String title, Lines line) {
        Marker newMarker = map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(title)
        );
        TrainMarker newtm = new TrainMarker(line, newMarker, vehicleNum);
        trainMarkers.add(newtm);
    }

    public TrainMarker getTrainMarkerFromVehicleNum(String vehicleNum) {
        for (TrainMarker train : trainMarkers) {
            if (train.GetVehicleNum().equals(vehicleNum)) {
                return train;
            }
        }
        return null;
    }

    public void addFavoriteStation(View view)
    {
        if(dataManager == null)
            dataManager = DataStorageManager.getInstance();
        dataManager.SetContext(this); //TODO: Move to onResume()? or other method?

        //Save it
        dataManager.SaveStationFavorite(station.getStationID(), station.getStationName());
    }

}

