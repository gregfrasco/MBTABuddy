package mbta.mbtaAPI;

/**
 * Created by frascog on 2/4/16.
 */
public class ParentStation {

    private String stationName;
    private float Longitude;
    private float Latitude;
    private Station inbound;
    private Station outbound;

    public ParentStation(Stop stop){
        this.stationName = stop.getParentStationName();
        this.Longitude = Float.parseFloat(stop.getStopLon());
        this.Latitude = Float.parseFloat(stop.getStopLat());
        if(stop.getStopName().contains("Inbound")){
            this.inbound = new Station(this,stop);
        } else {
            this.outbound = new Station(this,stop);
        }
    }

    public void addStop(Stop stop) {
        if(stop.getStopName().contains("Inbound")){
            this.inbound = new Station(this,stop);
        } else {
            this.outbound = new Station(this,stop);
        }
    }

    public String getName() {
        return this.stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public Station getInbound() {
        return inbound;
    }

    public void setInbound(Station inbound) {
        this.inbound = inbound;
    }

    public Station getOutbound() {
        return outbound;
    }

    public void setOutbound(Station outbound) {
        this.outbound = outbound;
    }
}
