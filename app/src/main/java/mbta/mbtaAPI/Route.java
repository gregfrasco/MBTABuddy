
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import mbta.LineType;

@Generated("org.jsonschema2pojo")
public class Route {

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("direction")
    @Expose
    private List<Direction> direction = new ArrayList<Direction>();
    private LineType lineType;
    /**
     * 
     * @param routeId
     * @param routeName
     */
    public Route(String routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }

    /**
     * 
     * @return
     *     The routeId
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * 
     * @return
     *     The routeName
     */
    public String getRouteName() {
        return routeName;
    }


    /**
     *
     * @return
     * The direction
     */
    public List<Direction> getDirection() {
        return direction;
    }

    /**
     *
     * @param direction
     * The direction
     */
    public void setDirection(List<Direction> direction) {
        this.direction = direction;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }
}
