package mbta;

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
}
