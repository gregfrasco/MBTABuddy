package mbta.mbtabuddy.googleMaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
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
    private MarkerOptions options;

    public StationMarker(Station station) {
        this.station = station;
        this.options = new MarkerOptions()
                .position(station.getLatLan())
                .draggable(false)
                .title(station.getStationName())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.station))
                .anchor(0.5f,0.5f)
                .snippet(station.getStationName());
    }

    public StationMarker(Station station,int zoomLevel) {
        this.station = station;
        Bitmap icon = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.drawable.station);
        int width = icon.getWidth()/zoomLevel + 1;
        int height = icon.getHeight()/zoomLevel + 1;
        Bitmap.createScaledBitmap(icon, width,height/zoomLevel, false);
        BitmapDescriptor marker = BitmapDescriptorFactory.fromBitmap(icon);
        this.options = new MarkerOptions()
                .position(station.getLatLan())
                .draggable(false)
                .title(station.getStationName())
                .icon(marker)
                .anchor(0.5f,0.5f)
                .snippet(station.getStationName());
    }

    public MarkerOptions getMarkerOptions(){
        return options;
    }
}
