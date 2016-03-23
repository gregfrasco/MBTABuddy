package mbta;

import com.Activities.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by frascog on 2/17/16.
 */
public class Station {

    private List<Line> lines;
    private String stationID;
    private List<Stop> stopIDs;
    private String stationName;
    private double latitue;
    private double longitude;
    private ArrivalTime arrivalTimes;

    public Station(mbta.mbtaAPI.Stop stop) {
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

    public ArrivalTime getArrivalTimes(String stopID) {
        return new ArrivalTime(stopID);
    }

    public void setArrivalTimes(ArrivalTime arrivalTimes) {
        this.arrivalTimes = arrivalTimes;
    }

    public LatLng getLatLan() {
        return new LatLng(this.getLatitude(),this.getLongitude());
    }

    public List<Stop> getStopIDs() {
        if(stopIDs == null) {
            try{
                List<Stop> stops = new ArrayList<Stop>();
                InputStream is = MainActivity.context.getAssets().open("StopIDs.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine(); // remove headers
                while((line = br.readLine()) != null) {
                    if (line.contains(this.stationID)) {
                        String[] numberOfStops = Arrays.copyOfRange(line.split(","), 1, line.split(",").length);
                        for (String stop : numberOfStops) {
                            String[] newStop = line.split(";");
                            stops.add(new Stop(newStop[0], newStop[1], newStop[2], newStop[3]));
                        }
                        break;
                    }
                }
                this.stopIDs = stops;
            } catch (IOException e) {
                this.stopIDs = new ArrayList<Stop>();
                this.stopIDs = MBTA.getInstance().getAllStops(this, this.getLine());
            }
        }
        return stopIDs;
    }

    @Override
    public String toString() {
        return this.getStationName() + "," + this.getLatitude() + "," + this.getLongitude();
    }
}
