
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AlertsByStop {

    @SerializedName("alerts")
    @Expose
    private List<Alert> alerts = new ArrayList<Alert>();
    @SerializedName("stop_id")
    @Expose
    private String stopId;
    @SerializedName("stop_name")
    @Expose
    private String stopName;

    /**
     * 
     * @return
     *     The alerts
     */
    public List<Alert> getAlerts() {
        return alerts;
    }

    /**
     * 
     * @param alerts
     *     The alerts
     */
    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
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
