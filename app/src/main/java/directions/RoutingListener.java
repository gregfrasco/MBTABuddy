package directions;

import java.util.ArrayList;

public interface RoutingListener {
    void onRoutingFailure(RouteException e);

    void onRoutingStart();

    void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex,int color);

    void onRoutingCancelled();
}
