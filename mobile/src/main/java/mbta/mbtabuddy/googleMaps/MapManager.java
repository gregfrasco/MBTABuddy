package mbta.mbtabuddy.googleMaps;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mbta.mbtabuddy.mbta.Line;
import mbta.mbtabuddy.mbta.LineType;
import mbta.mbtabuddy.mbta.Lines;
import mbta.mbtabuddy.mbta.Station;


/**
 * Created by frascog on 8/4/16.
 */

public class MapManager {

    private Context context;
    private GoogleMap map;

    public MapManager(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;
    }

    public MapManager(Context context) {
        this.context = context;
    }

    public void setMap(GoogleMap map){
        this.map = map;
    }

    public void drawLine(Line line){
        line.drawLine(map);
    }

    public void drawLine(Line line,boolean subway){
        line.drawLine(map,subway);
    }

    public void drawAllLines() {
        for (Line line: Lines.getInstance().values()){
            this.drawLine(line);
        }
    }

    public void drawAllLines(boolean subway) {
        for (Line line: Lines.getInstance().values()) {
            if ((line.getType().equals(LineType.Subway) || line.getType().equals(LineType.Tram))) {
                this.drawLine(line, subway);
            } else {
                this.drawLine(line, !subway);
            }
        }
    }

    public void addAllStations() {
        for (Line line: Lines.getInstance().values()){
            this.addStation(line);
        }
    }

    public void addAllStations(boolean subway) {
        for (Line line: Lines.getInstance().values()){
            if(!subway){
                if(!(line.getType().equals(LineType.Subway) || line.getType().equals(LineType.Tram))){
                    this.addStation(line);
                }
            } else {
                this.addStation(line);
            }
        }
    }

    private void addStation(Line line) {
        for(Station station: line.getStations()){
            StationMarker stationMarker = new StationMarker(station);
            map.addMarker(stationMarker.getMarkerOptions());
        }
    }
}
