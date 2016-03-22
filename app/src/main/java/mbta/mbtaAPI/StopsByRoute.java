
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class StopsByRoute {

    @SerializedName("direction")
    @Expose
    private List<Direction> direction = new ArrayList<Direction>();

    /**
     * 
     * @return
     *     The direction
     */
    public List<Direction> getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     *     The direction
     */
    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

}
