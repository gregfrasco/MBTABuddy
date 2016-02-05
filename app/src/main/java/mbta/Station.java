package mbta;

/**
 * Created by frascog on 2/4/16.
 */
public class Station {

    private ParentStation station;
    private int stopOrder;
    private int stopID;

    public Station(ParentStation parentStation,Stop stop) {
        this.station = parentStation;
        this.stopID = Integer.parseInt(stop.getStopId());
        this.stopOrder = Integer.parseInt(stop.getStopOrder());
    }

}
