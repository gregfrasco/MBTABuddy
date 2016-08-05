package mbta.mbtabuddy.mbta;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mbta.mbtabuddy.MainActivity;
import mbta.mbtabuddy.mbta.mbtaAPI.Route;
import mbta.mbtabuddy.mbta.mbtaAPI.Vehicle;


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

    public Line(Route route, int color) {
        this.lineID = route.getRouteId();
        this.lineName = route.getRouteName();
        //this.type = route.getLineType();
        this.stations = getStations();
        this.getTerminalStations();
        this.color = color;
    }

    public Line(String lineID,String lineName,LineType type,int color){
        this.lineID = lineID;
        this.lineName = lineName;
        this.type = type;
        this.stations = getStations();
        this.getTerminalStations();
        this.color = color;
    }

    public Line(String lines,int color){
        Route route = MBTA.getInstance().getRoute(lines);
        this.lineID = route.getRouteId();
        this.lineName = route.getRouteName();
        //this.type = route.getLineType();
        this.stations = getStations();
        this.getTerminalStations();
        this.color = color;
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
        if(this.stations == null){
            this.initStations();
        }
        return stations;
    }

    private void initStations(){
        List<Station> stations = new ArrayList<Station>();
        try{
            InputStream is = MainActivity.context.getAssets().open("Stations.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine(); // remove headers
            while((line = br.readLine()) != null){
                List<String> stationInfo = new ArrayList<String>(Arrays.asList(line.split(",")));
                if(stationInfo.contains(this.getLineID())){
                    stations.add(new Station(stationInfo));
                }
            }
            this.stations = stations;
        } catch (IOException e) {
            this.stations = MBTA.getInstance().getStationsByLine(this);
        }
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

    public List<LatLng> getMapPoints(){
        if(mapPoints == null) {
            try {
                List<LatLng> points = new ArrayList<LatLng>();
                InputStream is = MainActivity.context.getAssets().open(lineID+".txt");
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
        if(this.mapPoints == null){
            this.mapPoints = this.getMapPoints();
        }
        if(this.mapPoints == null){
            return;
        }
        map.addPolyline(new PolylineOptions().width(20).color(this.getColor()).zIndex(1).addAll(this.mapPoints));
        //line border
        map.addPolyline(new PolylineOptions().width(30).color(Color.BLACK).zIndex(0).addAll(this.mapPoints));
    }

    public void drawLine(GoogleMap map, boolean subway) {
        if(this.mapPoints == null){
            this.mapPoints = this.getMapPoints();
        }
        if(this.mapPoints == null){
            return;
        }
        int zIndex = 0;

        if(subway){
            zIndex += 2;
        }
        map.addPolyline(new PolylineOptions().width(20).color(this.getColor()).zIndex(zIndex+1).addAll(this.mapPoints));
        //line border
        map.addPolyline(new PolylineOptions().width(30).color(Color.BLACK).zIndex(zIndex).addAll(this.mapPoints));
    }

    public void adjustStations(){
        for(Station station: this.getStations()){
            Location stationLocation = new Location("");
            stationLocation.setLongitude(station.getLongitude());
            stationLocation.setLatitude(station.getLatitude());
            LatLng closestPoint = null;
            float distance = 10000;
            for(LatLng point: this.getMapPoints()){
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
            station.setLatitude(closestPoint.latitude);
        }
    }

    public void adjustVehicles(Vehicle vehicle){
        Location vehicleLocation = new Location("");
        vehicleLocation.setLongitude(vehicle.getVehicleLon());
        vehicleLocation.setLatitude(vehicle.getVehicleLat());
        LatLng closestPoint = null;
        float distance = 10000;
        for(LatLng point: this.getMapPoints()){
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



    public void getTerminalStations() {
        try{
            List<Station> stations = new ArrayList<Station>();
            InputStream is = MainActivity.context.getAssets().open("TerminalStations.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine()) != null){
                String[] terminiInfo = line.split(",");
                if(terminiInfo[0].equals(this.getLineID())){
                    for(Station station: this.stations){
                        if(station.getStationName().equals(terminiInfo[1])){
                            this.setTerminalStation1(station);
                        }
                        if(station.getStationName().equals(terminiInfo[2])){
                            this.setTerminalStation2(station);
                        }
                    }
                }
            }
        } catch (IOException e) {
            if(this.stations.size() > 0) {
                this.setTerminalStation1(stations.get(0));
                this.setTerminalStation2(stations.get(stations.size() - 1));
            }
        }
    }
}
