
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PredictionsByTrip {

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("route_type")
    @Expose
    private String routeType;
    @SerializedName("mode_name")
    @Expose
    private String modeName;
    @SerializedName("trip_id")
    @Expose
    private String tripId;
    @SerializedName("trip_name")
    @Expose
    private String tripName;
    @SerializedName("trip_headsign")
    @Expose
    private String tripHeadsign;
    @SerializedName("direction_id")
    @Expose
    private String directionId;
    @SerializedName("direction_name")
    @Expose
    private String directionName;
    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;
    @SerializedName("stop")
    @Expose
    private List<Stop> stop = new ArrayList<Stop>();

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
     *     The tripId
     */
    public String getTripId() {
        return tripId;
    }

    /**
     * 
     * @param tripId
     *     The trip_id
     */
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    /**
     * 
     * @return
     *     The tripName
     */
    public String getTripName() {
        return tripName;
    }

    /**
     * 
     * @param tripName
     *     The trip_name
     */
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    /**
     * 
     * @return
     *     The tripHeadsign
     */
    public String getTripHeadsign() {
        return tripHeadsign;
    }

    /**
     * 
     * @param tripHeadsign
     *     The trip_headsign
     */
    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
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
     *     The vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * 
     * @param vehicle
     *     The vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * 
     * @return
     *     The stop
     */
    public List<Stop> getStop() {
        return stop;
    }

    /**
     * 
     * @param stop
     *     The stop
     */
    public void setStop(List<Stop> stop) {
        this.stop = stop;
    }

}
