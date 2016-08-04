package mbta.mbtabuddy.mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import mbta.mbtabuddy.mbta.LineType;


@Generated("org.jsonschema2pojo")
public class Routes {

    @SerializedName("mode")
    @Expose
    private List<Mode> mode = new ArrayList<Mode>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Routes() {
    }

    /**
     * 
     * @param mode
     */
    public Routes(List<Mode> mode) {
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

    public Routes withMode(List<Mode> mode) {
        this.mode = mode;
        return this;
    }

    public List<Route> getRoutes(){
        List<Route> routes = new ArrayList<Route>();
        for(Mode mode: getMode()){
            LineType type = setType(mode.getRouteType());
            for(Route route:mode.getRoute()){
                route.setLineType(type);
                routes.add(route);
            }
        }
        return routes;
    }

    public LineType setType(String type) {
        switch(type){
            case "0":
                return LineType.Tram;
            case "1":
                return LineType.Subway;
            case "2":
                return LineType.Commuter_Rail;
            case "3":
                return LineType.Bus;
            case "4":
                return LineType.Boat;
        }
        return null;
    }
}
