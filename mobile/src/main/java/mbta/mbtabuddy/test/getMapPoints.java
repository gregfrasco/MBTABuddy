package mbta.mbtabuddy.test;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import mbta.mbtabuddy.directions.AbstractRouting;
import mbta.mbtabuddy.directions.Route;
import mbta.mbtabuddy.directions.RouteException;
import mbta.mbtabuddy.directions.Routing;
import mbta.mbtabuddy.directions.RoutingListener;
import mbta.mbtabuddy.mbta.Line;

/**
 * Created by frascog on 8/5/16.
 */

public class getMapPoints implements RoutingListener{

    public void drawLine(Line line) {
        if(line.getMapPoints() == null) {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.TRANSIT)
                    .waypoints(new LatLng(42.3525181,-71.0550605), new LatLng(42.124084,-71.103627))
                    .key("AIzaSyBK7kLLVvVV4Yn-thFj8rtw1OfHu0XiYe8")
                    .withListener(this)
                    .withLine(line)
                    .build();
            routing.execute();
        } else {
            //line.drawLine(map);
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.v("MBTA", "ROUTE FAILED");
        Log.v("MBTA",e.getMessage());
    }

    @Override
    public void onRoutingStart() {
        Log.v("MBTA", "ROUTE START");
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex, Line line) {
        route.get(0).getPoints().remove(0);
        route.get(0).getPoints().remove(route.get(0).getPoints().size() - 1);
        line.setMapPoints(route.get(0).getPoints());
        String message = "";
        for(LatLng point :line.getMapPoints()){
            message += point.latitude + "," + point.longitude + "\n";
        }
        System.out.println(message);
        message.toString();
    }

    @Override
    public void onRoutingCancelled() {

    }
}
