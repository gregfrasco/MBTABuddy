
package mbta;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Stop {

    @SerializedName("stop_order")
    @Expose
    private String stopOrder;
    @SerializedName("stop_id")
    @Expose
    private String stopId;
    @SerializedName("stop_name")
    @Expose
    private String stopName;
    @SerializedName("parent_station")
    @Expose
    private String parentStation;
    @SerializedName("parent_station_name")
    @Expose
    private String parentStationName;
    @SerializedName("stop_lat")
    @Expose
    private String stopLat;
    @SerializedName("stop_lon")
    @Expose
    private String stopLon;
    @SerializedName("distance")
    @Expose
    private String distance;

    /**
     * @return The stopOrder
     */
    public String getStopOrder() {
        return stopOrder;
    }

    /**
     * @param stopOrder The stop_order
     */
    public void setStopOrder(String stopOrder) {
        this.stopOrder = stopOrder;
    }

    /**
     * @return The stopId
     */
    public String getStopId() {
        return stopId;
    }

    /**
     * @param stopId The stop_id
     */
    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    /**
     * @return The stopName
     */
    public String getStopName() {
        return stopName;
    }

    /**
     * @param stopName The stop_name
     */
    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    /**
     * @return The parentStation
     */
    public String getParentStation() {
        return parentStation;
    }

    /**
     * @param parentStation The parent_station
     */
    public void setParentStation(String parentStation) {
        this.parentStation = parentStation;
    }

    /**
     * @return The parentStationName
     */
    public String getParentStationName() {
        return parentStationName;
    }

    /**
     * @param parentStationName The parent_station_name
     */
    public void setParentStationName(String parentStationName) {
        this.parentStationName = parentStationName;
    }

    /**
     * @return The stopLat
     */
    public String getStopLat() {
        return stopLat;
    }

    /**
     * @param stopLat The stop_lat
     */
    public void setStopLat(String stopLat) {
        this.stopLat = stopLat;
    }

    /**
     * @return The stopLon
     */
    public String getStopLon() {
        return stopLon;
    }

    /**
     * @param stopLon The stop_lon
     */
    public void setStopLon(String stopLon) {
        this.stopLon = stopLon;
    }


    /**
     * @return The distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance The distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }
}
