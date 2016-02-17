
package mbta.mbtaAPI;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class StopsByLocation {

    @SerializedName("stop")
    @Expose
    private List<Stop> stop = new ArrayList<Stop>();

    /**
     * 
     * @return
     *     The stop
     */
    public List<Stop> getStop() {
        return stop;
    }

    /**
     * 
     * @param stop
     *     The stop
     */
    public void setStop(List<Stop> stop) {
        this.stop = stop;
    }

}
