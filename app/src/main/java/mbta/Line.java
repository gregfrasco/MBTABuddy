package mbta;

import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import directions.AbstractRouting;
import directions.RouteException;
import directions.Routing;
import directions.RoutingListener;
import mbta.mbtaAPI.MBTARoutes;
import mbta.mbtaAPI.Route;
import mbta.mbtaAPI.Vehicle;
import mbta.mbtabuddy.R;

/**
 * Created by frascog on 2/17/16.
 */
public class Line {

    private Lines lines;
    private String lineID;
    private String lineName;
    private List<Station> stations;
    private LineType type;
    private Station terminalStation1;
    private Station terminalStation2;
    private List<LatLng> mapPoints;

    public Line(Route route) {
        MBTA mbta = MBTA.getInstance();
        this.lineID = route.getRouteId();
        this.lineName = route.getRouteName();
        this.type = route.getLineType();
        this.stations = mbta.getStationsByLine(this);
        Station[] stations = mbta.getTerminalStations(this);
        this.setTerminalStation1(stations[0]);
        this.setTerminalStation2(stations[1]);
        this.lines = Lines.getLine(this.lineID);
    }

    public Line(Lines lines){
        Line line = MBTA.getInstance().getLine(lines);
        this.lines = lines;
        this.setLineID(line.getLineID());
        this.setLineName(line.getLineName());
        this.setType(line.getType());
        this.setStations(line.getStations());
        this.setTerminalStation1(line.getTerminalStation1());
        this.setTerminalStation2(line.getTerminalStation2());
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
        int color = Color.BLUE;
        switch (lines){
            case Red_Line:
            case Mattapan_Line:
                color = Color.RED;
                break;
            case Orange_Line:
                color = Color.rgb(255,165,0);
                break;
            case Green_Line_B:
            case Green_Line_C:
            case Green_Line_D:
            case Green_Line_E:
                color = Color.GREEN;
                break;
            case Blue_Line:
                color = Color.BLUE;
                break;

        }
        return color;
    }

    public Lines getLines() {
        return this.lines;
    }

    public List<LatLng> getMapPoints(){
        if(mapPoints == null){

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
            station.setLatitue(closestPoint.latitude);
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
}
