package mbta;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

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
        this.setLatitude(Double.parseDouble(stop.getStopLat()));
        this.setLongitude(Double.parseDouble(stop.getStopLon()));
    }

    public Station(List<String> stationInfo) {
        this.setStationID(stationInfo.get(0));
        this.setStationName(stationInfo.get(1));
        this.setLatitude(Double.parseDouble(stationInfo.get(2)));
        this.setLongitude(Double.parseDouble(stationInfo.get(3)));

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

    public double getLatitude() {
        return latitue;
    }

    public void setLatitude(double latitude) {
        this.latitue = latitude;
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
        return new LatLng(this.getLatitude(),this.getLongitude());
    }

    public List<String> getStopIDs() {
        if(stopIDs == null) {
            this.stopIDs = new ArrayList<String>();
            this.stopIDs = MBTA.getInstance().getAllStops(this, this.getLine());
        }
        return stopIDs;
    }

    @Override
    public String toString() {
        return this.getStopIDs()+ "," + this.getStationName() + "," + this.getLatitude() + "," + this.getLongitude();
    }
}
