package mbta;

import java.util.List;

import mbta.mbtaAPI.Route;

/**
 * Created by frascog on 2/17/16.
 */
public class Line {

    private String lineID;
    private String lineName;
    private List<Station> stations;
    private LineType type;

    //TODO
    public Line(Route route) {
        MBTA mbta = MBTA.getInstance();
        this.lineID = route.getRouteId();
        this.lineName = route.getRouteName();
        this.type = route.getLineType();
    }
}
