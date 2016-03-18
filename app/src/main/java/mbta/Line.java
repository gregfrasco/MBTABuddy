package mbta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import mbta.mbtaAPI.Route;
import mbta.mbtaAPI.Vehicle;
import mbta.mbtabuddy.R;

/**
 * Created by frascog on 2/17/16.
 */
public class Line {

    private String lineID;
    private String lineName;
    private List<Station> stations;
    private LineType type;
    private Station terminalStation1;
    private Station terminalStation2;
    private List<LatLng> mapPoints;
    private int color;

    public Line(Route route,int color) {
        MBTA mbta = MBTA.getInstance();
        this.lineID = route.getRouteId();
        this.lineName = route.getRouteName();
        this.type = route.getLineType();
        this.stations = getStations();
        Station[] stations = mbta.getTerminalStations(this);
        this.setTerminalStation1(stations[0]);
        this.setTerminalStation2(stations[1]);
        this.color = color;
    }

    public Line(String lines,int color){
        this(MBTA.getInstance().getRoute(lines),color);
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public List<Station> getStations() {
        if(stations == null){
            this.stations = MBTA.getInstance().getStationsByLine(this);
        }
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    public Station getTerminalStation2() {
        return terminalStation2;
    }

    public void setTerminalStation2(Station terminalStation2) {
        this.terminalStation2 = terminalStation2;
    }

    public Station getTerminalStation1() {
        return terminalStation1;
    }

    public void setTerminalStation1(Station terminalStation1) {
        this.terminalStation1 = terminalStation1;
    }

    public int getColor() {
        return color;
    }

    public List<LatLng> getMapPoints(Context context){
        if(mapPoints == null) {
            try {
                List<LatLng> points = new ArrayList<LatLng>();
                InputStream is = context.getAssets().open(lineID+".txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";
                while((line = br.readLine()) != null){
                    String[] point = line.split(",");
                    points.add(new LatLng(Double.parseDouble(point[0]),Double.parseDouble(point[1])));
                }
                this.mapPoints = points;
            } catch (IOException e) {
                //return null;
            }
        }
        return this.mapPoints;
    }


    public void setMapPoints(List<LatLng> mapPoints) {
        this.mapPoints = mapPoints;
    }

    public void drawLine(GoogleMap map) {
        //line
        map.addPolyline(new PolylineOptions().width(20).color(this.getColor()).zIndex(1).addAll(this.mapPoints));
        //line border
        map.addPolyline(new PolylineOptions().width(30).color(Color.BLACK).zIndex(0).addAll(this.mapPoints));
    }

    public void adjustStations(){
        for(Station station: this.getStations()){
            Location stationLocation = new Location("");
            stationLocation.setLongitude(station.getLongitude());
            stationLocation.setLatitude(station.getLatitue());
            LatLng closestPoint = null;
            float distance = 10000;
            for(LatLng point: this.getMapPoints(null)){
                Location testLocation = new Location("");
                testLocation.setLatitude(point.latitude);
                testLocation.setLongitude(point.longitude);
                float testDistance = stationLocation.distanceTo(testLocation);
                if(testDistance < distance){
                    distance = testDistance;
                    closestPoint = point;
                }
            }
            station.setLongitude(closestPoint.longitude);
            station.setLatitue(closestPoint.latitude);
        }
    }

    public void adjustVehicles(Vehicle vehicle){
        Location vehicleLocation = new Location("");
        vehicleLocation.setLongitude(vehicle.getVehicleLon());
        vehicleLocation.setLatitude(vehicle.getVehicleLat());
        LatLng closestPoint = null;
        float distance = 10000;
        for(LatLng point: this.getMapPoints(null)){
            Location testLocation = new Location("");
            testLocation.setLatitude(point.latitude);
            testLocation.setLongitude(point.longitude);
            float testDistance = vehicleLocation.distanceTo(testLocation);
            if(testDistance < distance){
                distance = testDistance;
                closestPoint = point;
            }
        }
        vehicle.setVehicleLon(closestPoint.longitude);
        vehicle.setVehicleLat(closestPoint.latitude);
    }
}
