package gmap;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import directions.AbstractRouting;
import directions.GoogleParser;
import directions.Route;
import directions.RouteException;
import directions.Routing;
import directions.RoutingListener;
import mbta.Line;
import mbta.Lines;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class MapManager implements RoutingListener {
    static MapManager instance;
    private Context context;
    private GoogleMap map;
    private ArrayList<Polyline> polylines;
    private Line line;

    //TrainMarker objects, keys being their MBTA trip num from MBTA api
    private List<TrainMarker> trainMarkers = new ArrayList<TrainMarker>();
    private List<StationMarker> stationMarkers = new ArrayList<StationMarker>();

    public static MapManager getInstance() {
        if (instance == null)
            instance = new MapManager();
        return instance;
    }

    public void SetContext(Context con) {
        context = con;
    }

    public void SetMap(GoogleMap _map) {
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

    public void ZoomToLocation(LatLng location, int zoomAmnt) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomAmnt));
    }

    //region TrainMarkers

    // Move a train already on the map
    public void MoveTrainMarker(String tripNum, LatLng newPos) {
        TrainMarker train = GetTrainMarkerFromVehicleNum(tripNum);
        train.GetMarker().setPosition(newPos);
    }

    public TrainMarker GetTrainMarkerFromId(String markerId) {
        for (TrainMarker train : trainMarkers) {
            String id = train.GetMarker().getId();
            if (id.equals(markerId)) {
                return train;
            }
        }
        return null;
    }

    /**
     * @param vehicleNum Given by MBTA API
     * @return TrainMarker object associated with this tripNum on creation
     */
    public TrainMarker GetTrainMarkerFromVehicleNum(String vehicleNum) {
        for (TrainMarker train : trainMarkers) {
            if (train.GetVehicleNum().equals(vehicleNum)) {
                return train;
            }
        }

        return null;
    }

    public void ZoomTwoPoints(LatLng swPoint, LatLng nePoint) {
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(swPoint, nePoint), 10));
    }

    public void ZoomToTrainMarker(String vehicleNum, int zoomNum) {
        TrainMarker train = GetTrainMarkerFromVehicleNum(vehicleNum);
        LatLng position = train.GetMarker().getPosition();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomNum));
    }

    //Add a train to the map
    public void AddTrainMarker(String vehicleNum, LatLng location, String title, Lines line) {
        Marker newMarker = map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(title)
        );

        TrainMarker newtm = new TrainMarker(line, newMarker, vehicleNum);
        trainMarkers.add(newtm);
    }

    //endregion

    //region StationMarkers
    public StationMarker GetStationMarker(String stationName) {
        for (StationMarker stat : stationMarkers) {
            if (stat.getStationName().equals(stationName))
                return stat;
        }

        return null;
    }

    public StationMarker GetStationMarkerById(String markerId) {
        for (StationMarker stat : stationMarkers) {
            String id = stat.getMarker().getId();
            if (id.equals(markerId)) {
                return stat;
            }
        }

        return null;
    }

    public void ZoomToStationMarker(String stationName, int zoomNum) {
        StationMarker sm = GetStationMarker(stationName);
        LatLng pos = sm.getMarker().getPosition();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoomNum));
    }

    public void AddStationMarker(String stationName, LatLng location) {
        Marker newMarker = map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(stationName)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_station))
        );

        StationMarker newsm = new StationMarker(stationName, newMarker);
        stationMarkers.add(newsm);
    }

    public void drawLine(Line line) {
        this.line = line;
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.TRANSIT)
                .waypoints(line.getTerminalStation1().getLatLan(), line.getTerminalStation2().getLatLan())
                .key(context.getString(R.string.google_maps_directions_api_key))
                .withListener(this)
                .build();
        routing.execute();
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {
        // The Routing Request starts
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        polylines = new ArrayList<>();
        route.get(0).getPoints().remove(0);
        route.get(0).getPoints().remove(route.get(0).getPoints().size()-1);
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(R.color.colorAccent);
        polyOptions.width(30);
        polyOptions.addAll(route.get(0).getPoints());
        Polyline polyline = map.addPolyline(polyOptions);
        polylines.add(polyline);
    }

    @Override
    public void onRoutingCancelled() {

    }
}
