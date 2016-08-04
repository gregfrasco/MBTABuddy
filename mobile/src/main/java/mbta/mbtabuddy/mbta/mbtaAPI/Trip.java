package mbta.mbtabuddy.mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Trip {

    @SerializedName("trip_id")
    @Expose
    private String tripId;
    @SerializedName("trip_name")
    @Expose
    private String tripName;
    @SerializedName("trip_headsign")
    @Expose
    private String tripHeadsign;
    @SerializedName("sch_arr_dt")
    @Expose
    private String schArrDt;
    @SerializedName("sch_dep_dt")
    @Expose
    private String schDepDt;
    @SerializedName("pre_dt")
    @Expose
    private String preDt;
    @SerializedName("pre_away")
    @Expose
    private String preAway;
    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;
    @SerializedName("stop")
    @Expose
    private List<Stop> stop;



    public void setStop(List<Stop> _stop)
    {
        stop = stop;
    }

    public List<Stop> getStop()
    {
        return stop;
    }
    /**
     *
     * @return
     * The tripId
     */
    public String getTripId() {
        return tripId;
    }

    /**
     *
     * @param tripId
     * The trip_id
     */
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    /**
     *
     * @return
     * The tripName
     */
    public String getTripName() {
        return tripName;
    }

    /**
     *
     * @param tripName
     * The trip_name
     */
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    /**
     *
     * @return
     * The tripHeadsign
     */
    public String getTripHeadsign() {
        return tripHeadsign;
    }

    /**
     *
     * @param tripHeadsign
     * The trip_headsign
     */
    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
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
     *
     * @return
     * The vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     *
     * @param vehicle
     * The vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
