package gmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;
import mbta.Lines;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class MapManager {
    static MapManager instance;
    private Context context;
    private GoogleMap map;

    //TrainMarker objects, keys being their MBTA trip num from MBTA api
    private List<TrainMarker> trainMarkers = new ArrayList<TrainMarker>();
    private List<StationMarker> stationMarkers = new ArrayList<StationMarker>();

    public static MapManager getInstance()
    {
        if(instance == null)
            instance = new MapManager();
        return instance;
    }

    public void SetContext(Context con)
    {
        context = con;
    }

    public void SetMap(GoogleMap _map)
    {
        map = _map;
        map.setInfoWindowAdapter(new TrainInfoWindow(context));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //For train markers
                if (GetTrainMarkerFromId(marker.getId()) != null) {
                    //...
                    return false;//We want to show info window
                }

                //For station markers
                if (GetStationMarkerById(marker.getId()) != null) {
                    //...
                    return true;
                }

                return true;//True overrides default behavior
            }
        });
    }

    public void ZoomToLocation(LatLng location, int zoomAmnt)
    {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomAmnt));
    }

    //region TrainMarkers

    // Move a train already on the map
    public void MoveTrainMarker(String tripNum, LatLng newPos)
    {
        TrainMarker train = GetTrainMarkerFromVehicleNum(tripNum);
        train.GetMarker().setPosition(newPos);
    }

    public TrainMarker GetTrainMarkerFromId(String markerId)
    {
        for(TrainMarker train : trainMarkers)
        {
            String id = train.GetMarker().getId();
            if(id.equals(markerId))
            {
                return train;
            }
        }
        return null;
    }

    /**
     * @param vehicleNum
     * Given by MBTA API
     *
     * @return
     * TrainMarker object associated with this tripNum on creation
     */
    public TrainMarker GetTrainMarkerFromVehicleNum(String vehicleNum)
    {
        for(TrainMarker train : trainMarkers)
        {
            if(train.GetVehicleNum().equals(vehicleNum))
            {
                return train;
            }
        }

        return null;
    }

    public void ZoomTwoPoints(LatLng swPoint, LatLng nePoint)
    {
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(swPoint, nePoint), 10));
    }

    public void ZoomToTrainMarker(String vehicleNum, int zoomNum)
    {
        TrainMarker train = GetTrainMarkerFromVehicleNum(vehicleNum);
        LatLng position = train.GetMarker().getPosition();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomNum));
    }

    //Add a train to the map
    public void AddTrainMarker(String vehicleNum, LatLng location, String title, Lines line)
    {
        Marker newMarker = map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(title)
        );

        TrainMarker newtm = new TrainMarker(line, newMarker, vehicleNum);
        trainMarkers.add(newtm);
    }

    //endregion

    //region StationMarkers
    public StationMarker GetStationMarker(String stationName)
    {
        for(StationMarker stat : stationMarkers)
        {
            if(stat.getStationName().equals(stationName))
                return stat;
        }

        return null;
    }

    public StationMarker GetStationMarkerById(String markerId)
    {
        for (StationMarker stat : stationMarkers)
        {
            String id = stat.getMarker().getId();
            if(id.equals(markerId)){
                return stat;
            }
        }

        return null;
    }

    public void ZoomToStationMarker(String stationName, int zoomNum)
    {
        StationMarker sm = GetStationMarker(stationName);
        LatLng pos = sm.getMarker().getPosition();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoomNum));
    }

    public void AddStationMarker(String stationName, LatLng location)
    {
        Marker newMarker = map.addMarker(new MarkerOptions()
                .position(location)
                .title(stationName)
        );

        StationMarker newsm = new StationMarker(stationName, newMarker);
        stationMarkers.add(newsm);
    }
    //endregion
}
