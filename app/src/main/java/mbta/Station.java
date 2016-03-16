package mbta;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import mbta.mbtaAPI.Route;
import mbta.mbtaAPI.Stop;

/**
 * Created by frascog on 2/17/16.
 */
public class Station {

    private List<Line> lines;
    private String stationID;
    private List<String> stopIDs;
    private String stationName;
    private double latitue;
    private double longitude;
    private ArrivalTime arrivalTimes;

    public Station(Stop stop) {
        this.setStationID(stop.getStopId());
        this.setStationName(stop.getParentStationName());
        this.setLatitue(Double.parseDouble(stop.getStopLat()));
        this.setLongitude(Double.parseDouble(stop.getStopLon()));
    }

    public List<Line> getLine() {
        if(lines == null) {
            this.lines = MBTA.getInstance().getRoutesByStop(this.stationID,this.stationName);
        }
        return lines;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getLatitue() {
        return latitue;
    }

    public void setLatitue(double latitue) {
        this.latitue = latitue;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrivalTime getArrivalTimes() {
        return new ArrivalTime(this);
    }

    public void setArrivalTimes(ArrivalTime arrivalTimes) {
        this.arrivalTimes = arrivalTimes;
    }

    public LatLng getLatLan() {
        return new LatLng(this.getLatitue(),this.getLongitude());
    }

    public List<String> getStopIDs() {
        if(stopIDs == null) {
            this.stopIDs = new ArrayList<String>();
            this.stopIDs = MBTA.getInstance().getAllStops(this, this.getLine());
        }
        return stopIDs;
    }
}
