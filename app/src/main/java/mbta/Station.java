package mbta;

import mbta.mbtaAPI.Stop;

/**
 * Created by frascog on 2/17/16.
 */
public class Station {

    private Line line;
    private String stationID;
    private String stationName;
    private double latitue;
    private double longitude;
    private String[] arrivalTimes;

    public Station(Stop stop) {
        this(stop,null);
    }

    public Station(Stop stop,Line line) {
        this.setStationID(stop.getStopId());
        this.setStationName(stop.getStopName());
        this.setLatitue(Double.parseDouble(stop.getStopLat()));
        this.setLongitude(Double.parseDouble(stop.getStopLon()));
        this.setLine(line);
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
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

    public void setArrivalTimes(String[] arrivalTimes) {
        this.arrivalTimes = arrivalTimes;
    }
}
