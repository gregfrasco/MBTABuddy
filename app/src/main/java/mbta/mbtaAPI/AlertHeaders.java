
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AlertHeaders {

    @SerializedName("alert_headers")
    @Expose
    private List<AlertHeader> alertHeaders = new ArrayList<AlertHeader>();

    /**
     * 
     * @return
     *     The alertHeaders
     */
    public List<AlertHeader> getAlertHeaders() {
        return alertHeaders;
    }

    /**
     * 
     * @param alertHeaders
     *     The alert_headers
     */
    public void setAlertHeaders(List<AlertHeader> alertHeaders) {
        this.alertHeaders = alertHeaders;
    }

}
