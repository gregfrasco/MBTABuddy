package gmap;

import android.graphics.Bitmap;
import android.view.LayoutInflater;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import mbta.MBTARoutes;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class MapManager {
    static MapManager instance;
    private LayoutInflater inflater;
    private GoogleMap map;

    //TrainMarker objects, keys being their MBTA trip num from MBTA api
    private List<TrainMarker> trainMarkers = new ArrayList<TrainMarker>();

    public static MapManager getInstance()
    {
        if(instance == null)
            instance = new MapManager();
        return instance;
    }

    public void SetLayoutInflater(LayoutInflater inf)
    {
        inflater = inf;
    }

    public void SetMap(GoogleMap _map)
    {
        map = _map;
        map.setInfoWindowAdapter(new TrainInfoWindow(inflater));
    }

    //Move a train already on the map
    public void MoveTrainMarker(String tripNum, LatLng newPos)
    {
        TrainMarker train = GetTrainMarkerFromTripNum(tripNum);
        train.GetMarker().setPosition(newPos);
    }

    public TrainMarker GetTrainMarkerFromId(String markerId)
    {
        for(TrainMarker train : trainMarkers)
        {
            if(train.GetMarker().getId() == markerId)
            {
                return train;
            }
        }

        return null;
    }

    public TrainMarker GetTrainMarkerFromTripNum(String tripNum)
    {
        for(TrainMarker train : trainMarkers)
        {
            if(train.GetTripNum() == tripNum)
            {
                return train;
            }
        }

        return null;
    }

    //Add a train to the map
    public void AddTrainMarker(String tripNum, LatLng location, String title,MBTARoutes.Routes route)
    {
        Marker newMarker = map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(title)
        );

        TrainMarker newtm = new TrainMarker(route, newMarker, tripNum);
        trainMarkers.add(newtm);
    }

}
