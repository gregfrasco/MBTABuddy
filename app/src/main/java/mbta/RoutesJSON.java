
package mbta;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RoutesJSON {

    @SerializedName("mode")
    @Expose
    private List<Mode> mode = new ArrayList<Mode>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public RoutesJSON() {
    }

    /**
     * 
     * @param mode
     */
    public RoutesJSON(List<Mode> mode) {
        this.mode = mode;
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

    public RoutesJSON withMode(List<Mode> mode) {
        this.mode = mode;
        return this;
    }

}
