
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ScheduleByTrip {

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("direction")
    @Expose
    private List<Direction> direction = new ArrayList<Direction>();

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
     * @param routeId
     *     The route_id
     */
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    /**
     * 
     * @return
     *     The routeName
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * 
     * @param routeName
     *     The route_name
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * 
     * @return
     *     The direction
     */
    public List<Direction> getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     *     The direction
     */
    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

}
