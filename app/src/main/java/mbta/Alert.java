
package mbta;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Alert {

    @SerializedName("alert_id")
    @Expose
    private int alertId;
    @SerializedName("effect_name")
    @Expose
    private String effectName;
    @SerializedName("effect")
    @Expose
    private String effect;
    @SerializedName("cause_name")
    @Expose
    private String causeName;
    @SerializedName("cause")
    @Expose
    private String cause;
    @SerializedName("header_text")
    @Expose
    private String headerText;
    @SerializedName("short_header_text")
    @Expose
    private String shortHeaderText;
    @SerializedName("description_text")
    @Expose
    private String descriptionText;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("created_dt")
    @Expose
    private String createdDt;
    @SerializedName("last_modified_dt")
    @Expose
    private String lastModifiedDt;
    @SerializedName("service_effect_text")
    @Expose
    private String serviceEffectText;
    @SerializedName("timeframe_text")
    @Expose
    private String timeframeText;
    @SerializedName("alert_lifecycle")
    @Expose
    private String alertLifecycle;
    @SerializedName("effect_periods")
    @Expose
    private List<EffectPeriod> effectPeriods = new ArrayList<EffectPeriod>();
    @SerializedName("affected_services")
    @Expose
    private AffectedServices affectedServices;
    @SerializedName("recurrence_text")
    @Expose
    private String recurrenceText;

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

    /**
     * 
     * @return
     *     The effect
     */
    public String getEffect() {
        return effect;
    }

    /**
     * 
     * @param effect
     *     The effect
     */
    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * 
     * @return
     *     The causeName
     */
    public String getCauseName() {
        return causeName;
    }

    /**
     * 
     * @param causeName
     *     The cause_name
     */
    public void setCauseName(String causeName) {
        this.causeName = causeName;
    }

    /**
     * 
     * @return
     *     The cause
     */
    public String getCause() {
        return cause;
    }

    /**
     * 
     * @param cause
     *     The cause
     */
    public void setCause(String cause) {
        this.cause = cause;
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
     *     The shortHeaderText
     */
    public String getShortHeaderText() {
        return shortHeaderText;
    }

    /**
     * 
     * @param shortHeaderText
     *     The short_header_text
     */
    public void setShortHeaderText(String shortHeaderText) {
        this.shortHeaderText = shortHeaderText;
    }

    /**
     * 
     * @return
     *     The descriptionText
     */
    public String getDescriptionText() {
        return descriptionText;
    }

    /**
     * 
     * @param descriptionText
     *     The description_text
     */
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    /**
     * 
     * @return
     *     The severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * 
     * @param severity
     *     The severity
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * 
     * @return
     *     The createdDt
     */
    public String getCreatedDt() {
        return createdDt;
    }

    /**
     * 
     * @param createdDt
     *     The created_dt
     */
    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * 
     * @return
     *     The lastModifiedDt
     */
    public String getLastModifiedDt() {
        return lastModifiedDt;
    }

    /**
     * 
     * @param lastModifiedDt
     *     The last_modified_dt
     */
    public void setLastModifiedDt(String lastModifiedDt) {
        this.lastModifiedDt = lastModifiedDt;
    }

    /**
     * 
     * @return
     *     The serviceEffectText
     */
    public String getServiceEffectText() {
        return serviceEffectText;
    }

    /**
     * 
     * @param serviceEffectText
     *     The service_effect_text
     */
    public void setServiceEffectText(String serviceEffectText) {
        this.serviceEffectText = serviceEffectText;
    }

    /**
     * 
     * @return
     *     The timeframeText
     */
    public String getTimeframeText() {
        return timeframeText;
    }

    /**
     * 
     * @param timeframeText
     *     The timeframe_text
     */
    public void setTimeframeText(String timeframeText) {
        this.timeframeText = timeframeText;
    }

    /**
     * 
     * @return
     *     The alertLifecycle
     */
    public String getAlertLifecycle() {
        return alertLifecycle;
    }

    /**
     * 
     * @param alertLifecycle
     *     The alert_lifecycle
     */
    public void setAlertLifecycle(String alertLifecycle) {
        this.alertLifecycle = alertLifecycle;
    }

    /**
     * 
     * @return
     *     The effectPeriods
     */
    public List<EffectPeriod> getEffectPeriods() {
        return effectPeriods;
    }

    /**
     * 
     * @param effectPeriods
     *     The effect_periods
     */
    public void setEffectPeriods(List<EffectPeriod> effectPeriods) {
        this.effectPeriods = effectPeriods;
    }

    /**
     * 
     * @return
     *     The affectedServices
     */
    public AffectedServices getAffectedServices() {
        return affectedServices;
    }

    /**
     * 
     * @param affectedServices
     *     The affected_services
     */
    public void setAffectedServices(AffectedServices affectedServices) {
        this.affectedServices = affectedServices;
    }

    /**
     * 
     * @return
     *     The recurrenceText
     */
    public String getRecurrenceText() {
        return recurrenceText;
    }

    /**
     * 
     * @param recurrenceText
     *     The recurrence_text
     */
    public void setRecurrenceText(String recurrenceText) {
        this.recurrenceText = recurrenceText;
    }

}
