
package mbta;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class EffectPeriod {

    @SerializedName("effect_start")
    @Expose
    private String effectStart;
    @SerializedName("effect_end")
    @Expose
    private String effectEnd;

    /**
     * 
     * @return
     *     The effectStart
     */
    public String getEffectStart() {
        return effectStart;
    }

    /**
     * 
     * @param effectStart
     *     The effect_start
     */
    public void setEffectStart(String effectStart) {
        this.effectStart = effectStart;
    }

    /**
     * 
     * @return
     *     The effectEnd
     */
    public String getEffectEnd() {
        return effectEnd;
    }

    /**
     * 
     * @param effectEnd
     *     The effect_end
     */
    public void setEffectEnd(String effectEnd) {
        this.effectEnd = effectEnd;
    }

}
