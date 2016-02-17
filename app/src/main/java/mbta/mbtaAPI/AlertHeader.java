
package mbta.mbtaAPI;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AlertHeader {

    @SerializedName("alert_id")
    @Expose
    private int alertId;
    @SerializedName("header_text")
    @Expose
    private String headerText;
    @SerializedName("effect_name")
    @Expose
    private String effectName;

    /**
     * 
     * @return
     *     The alertId
     */
    public int getAlertId() {
        return alertId;
    }

    /**
     * 
     * @param alertId
     *     The alert_id
     */
    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    /**
     * 
     * @return
     *     The headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * 
     * @param headerText
     *     The header_text
     */
    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    /**
     * 
     * @return
     *     The effectName
     */
    public String getEffectName() {
        return effectName;
    }

    /**
     * 
     * @param effectName
     *     The effect_name
     */
    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

}
