
package mbta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class Route {

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("direction")
    @Expose
    private List<Direction> direction = new ArrayList<Direction>();

    private List<ParentStation> stations;
    private RouteType routeType;
    /**
     * 
     * @param routeId
     * @param routeName
     */
    public Route(String routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }

    /**
     * 
     * @return
     *     The routeId
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * 
     * @return
     *     The routeName
     */
    public String getRouteName() {
        return routeName;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    /**
     *
     * @return
     * The direction
     */
    public List<Direction> getDirection() {
        return direction;
    }

    /**
     *
     * @param direction
     * The direction
     */
    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

    public List<ParentStation> getStations() {
        if(this.stations == null) {
            MBTA mbta = MBTA.getInstance();
            this.stations = mbta.getStopsByRoute(this);
        }
        return this.stations;
    }

}
