package mbta;

/**
 * Created by frascog on 2/4/16.
 */
public class Station {

    private ParentStation station;
    private String name;
    private int stopOrder;
    private String stopID;
    private double distance;
    private float latitude;
    private float longitude;

    public Station(ParentStation parentStation,Stop stop) {
        this.station = parentStation;
        this.stopID = stop.getStopId();
        this.stopOrder = Integer.parseInt(stop.getStopOrder());
        this.name = stop.getStopName();
        this.latitude = Float.parseFloat(stop.getStopLat());
        this.longitude = Float.parseFloat(stop.getStopLon());
    }

    public Station(Stop stop) {
        this.stopID = stop.getStopId();
        this.name = stop.getStopName();
        this.latitude = Float.parseFloat(stop.getStopLat());
        this.longitude = Float.parseFloat(stop.getStopLon());
        if(stop.getStopOrder() != null) {
            this.stopOrder = Integer.parseInt(stop.getStopOrder());
        }
        this.distance = Double.parseDouble(stop.getDistance());
    }

    public ParentStation getStation() {
        return station;
    }

    public void setStation(ParentStation station) {
        this.station = station;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(int stopOrder) {
        this.stopOrder = stopOrder;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
