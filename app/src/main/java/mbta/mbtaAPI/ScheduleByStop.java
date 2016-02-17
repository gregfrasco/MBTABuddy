
package mbta.mbtaAPI;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ScheduleByStop {

    @SerializedName("stop_id")
    @Expose
    private String stopId;
    @SerializedName("stop_name")
    @Expose
    private String stopName;
    @SerializedName("mode")
    @Expose
    private List<Mode> mode = new ArrayList<Mode>();

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

    /**
     * 
     * @return
     *     The mode
     */
    public List<Mode> getMode() {
        return mode;
    }

    /**
     * 
     * @param mode
     *     The mode
     */
    public void setMode(List<Mode> mode) {
        this.mode = mode;
    }

}
