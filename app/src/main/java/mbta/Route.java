
package mbta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Route {

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("route_name")
    @Expose
    private String routeName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Route() {
    }

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
     * @param routeId
     *     The route_id
     */
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Route withRouteId(String routeId) {
        this.routeId = routeId;
        return this;
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

    public Route withRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

}
