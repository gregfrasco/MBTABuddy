package mbta.mbtabuddy.googleMaps;

import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import mbta.mbtabuddy.MainActivity;
import mbta.mbtabuddy.R;
import mbta.mbtabuddy.mbta.Station;

/**
 * Created by frascog on 8/4/16.
 */

public class StationMarker {

    private Station station;

    public StationMarker(Station station) {
        this.station = station;
    }

    public MarkerOptions getMarkerOptions(){
        return new MarkerOptions()
                .position(station.getLatLan())
                .draggable(false)
                .title(station.getStationName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.station))
                .anchor(0.5f,0.5f)
                .snippet(station.getStationName());
    }
}
