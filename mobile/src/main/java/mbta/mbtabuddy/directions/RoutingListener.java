package mbta.mbtabuddy.directions;

import java.util.ArrayList;

import mbta.mbtabuddy.mbta.Line;


public interface RoutingListener {
    void onRoutingFailure(RouteException e);

    void onRoutingStart();

    void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex, Line line);

    void onRoutingCancelled();
}
