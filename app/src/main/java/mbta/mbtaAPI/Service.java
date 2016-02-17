
package mbta.mbtaAPI;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Service {

    @SerializedName("route_type")
    @Expose
    private String routeType;
    @SerializedName("mode_name")
    @Expose
    private String modeName;
    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("direction_id")
    @Expose
    private String directionId;
    @SerializedName("direction_name")
    @Expose
    private String directionName;
    @SerializedName("stop_id")
    @Expose
    private String stopId;
    @SerializedName("stop_name")
    @Expose
    private String stopName;

    /**
     * 
     * @return
     *     The routeType
     */
    public String getRouteType() {
        return routeType;
    }

    /**
     * 
     * @param routeType
     *     The route_type
     */
    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    /**
     * 
     * @return
     *     The modeName
     */
    public String getModeName() {
        return modeName;
    }

    /**
     * 
     * @param modeName
     *     The mode_name
     */
    public void setModeName(String modeName) {
        this.modeName = modeName;
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
     *     The directionId
     */
    public String getDirectionId() {
        return directionId;
    }

    /**
     * 
     * @param directionId
     *     The direction_id
     */
    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    /**
     * 
     * @return
     *     The directionName
     */
    public String getDirectionName() {
        return directionName;
    }

    /**
     * 
     * @param directionName
     *     The direction_name
     */
    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    /**
     * 
     * @return
     *     The stopId
     */
    public String getStopId() {
        return stopId;
    }

    /**
     * 
     * @param stopId
     *     The stop_id
     */
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    /**
     * 
     * @return
     *     The stopName
     */
    public String getStopName() {
        return stopName;
    }

    /**
     * 
     * @param stopName
     *     The stop_name
     */
    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

}
