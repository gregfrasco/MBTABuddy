
package mbta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Trip {

    @SerializedName("trip_id")
    @Expose
    private String tripId;
    @SerializedName("trip_name")
    @Expose
    private String tripName;
    @SerializedName("sch_arr_dt")
    @Expose
    private String schArrDt;
    @SerializedName("sch_dep_dt")
    @Expose
    private String schDepDt;

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
     *     The schArrDt
     */
    public String getSchArrDt() {
        return schArrDt;
    }

    /**
     * 
     * @param schArrDt
     *     The sch_arr_dt
     */
    public void setSchArrDt(String schArrDt) {
        this.schArrDt = schArrDt;
    }

    /**
     * 
     * @return
     *     The schDepDt
     */
    public String getSchDepDt() {
        return schDepDt;
    }

    /**
     * 
     * @param schDepDt
     *     The sch_dep_dt
     */
    public void setSchDepDt(String schDepDt) {
        this.schDepDt = schDepDt;
    }

}
