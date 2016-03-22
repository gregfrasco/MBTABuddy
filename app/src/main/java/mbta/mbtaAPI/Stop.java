
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

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
    @SerializedName("sch_arr_dt")
    @Expose
    private String schArrDt;
    @SerializedName("sch_dep_dt")
    @Expose
    private String schDepDt;
    @SerializedName("stop_sequence")
    @Expose
    private String stopSequence;
    @SerializedName("pre_dt")
    @Expose
    private String preDt;
    @SerializedName("pre_away")
    @Expose
    private String preAway;



    /**
     *
     * @return
     * The preDt
     */
    public String getPreDt() {
        return preDt;
    }

    /**
     *
     * @param preDt
     * The pre_dt
     */
    public void setPreDt(String preDt) {
        this.preDt = preDt;
    }

    /**
     *
     * @return
     * The preAway
     */
    public String getPreAway() {
        return preAway;
    }

    /**
     *
     * @param preAway
     * The pre_away
     */
    public void setPreAway(String preAway) {
        this.preAway = preAway;
    }


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

    /**
     *
     * @return
     * The stopSequence
     */
    public String getStopSequence() {
        return stopSequence;
    }

    /**
     *
     * @param stopSequence
     * The stop_sequence
     */
    public void setStopSequence(String stopSequence) {
        this.stopSequence = stopSequence;
    }

    /**
     *
     * @return
     * The schArrDt
     */
    public String getSchArrDt() {
        return schArrDt;
    }

    /**
     *
     * @param schArrDt
     * The sch_arr_dt
     */
    public void setSchArrDt(String schArrDt) {
        this.schArrDt = schArrDt;
    }

    /**
     *
     * @return
     * The schDepDt
     */
    public String getSchDepDt() {
        return schDepDt;
    }

    /**
     *
     * @param schDepDt
     * The sch_dep_dt
     */
    public void setSchDepDt(String schDepDt) {
        this.schDepDt = schDepDt;
    }
}
