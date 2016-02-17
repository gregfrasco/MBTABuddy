package gmap;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by cruzj6 on 2/12/2016.
 */

public class StationMarker
{
    private Marker marker;
    private String stationName;

    StationMarker(String statName, Marker mapmarker)
    {
        marker = mapmarker;
        stationName = statName;
    }

    public String getStationName()
    {
        return stationName;
    }

    public Marker getMarker()
    {
        return marker;
    }
}
