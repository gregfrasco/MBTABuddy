package mbta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by frascog on 2/19/16.
 */
public class ArrivalTime {

    private HashMap<String,List<String>> timesPerStop;

    public ArrivalTime(Station station) {
        MBTA mbta = MBTA.getInstance();
        this.timesPerStop = new HashMap<String,List<String>>();
        for(String stopID :station.getStopIDs()){
            timesPerStop.put(stopID,mbta.getPredictionsByStop(stopID));
        }
    }

    public HashMap<String,List<String>> getTimes() {
        return timesPerStop;
    }
}
