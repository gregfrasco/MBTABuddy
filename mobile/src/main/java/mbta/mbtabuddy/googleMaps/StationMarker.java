package mbta.mbtabuddy.googleMaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import mbta.mbtabuddy.MainActivity;
import mbta.mbtabuddy.R;
import mbta.mbtabuddy.mbta.Line;
import mbta.mbtabuddy.mbta.Station;

/**
 * Created by frascog on 8/4/16.
 */

public class StationMarker {

    private Station station;
    private MarkerOptions options;

    public StationMarker(Station station) {
        this.station = station;
        String lines = "";
        /*
        for(Line line: this.station.getLine()){
            lines += line.getLineName() + ", ";
        }
        lines = lines.substring(0,lines.length()-3);
        */
        this.options = new MarkerOptions()
                .position(station.getLatLan())
                .draggable(false)
                .title(station.getStationName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.station))
                .anchor(0.5f,0.5f)
                .snippet(lines)
                .zIndex(4);
    }

    public MarkerOptions getMarkerOptions(){
        return options;
    }
}
