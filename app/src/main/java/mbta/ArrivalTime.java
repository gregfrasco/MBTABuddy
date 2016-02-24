package mbta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frascog on 2/19/16.
 */
public class ArrivalTime {

    private List<String> direction1Times;
    private List<String> direction2Times;

    public ArrivalTime(Station station) {
        MBTA mbta = MBTA.getInstance();
        String[] times = mbta.getPredictionsByStop(station);
        if(times.length >= 4){
            direction1Times = new ArrayList<>();
            direction2Times = new ArrayList<>();
            direction1Times.add(times[0]);
            direction1Times.add(times[1]);
            direction2Times.add(times[2]);
            direction2Times.add(times[3]);
        } else if(times.length >= 2){
            direction1Times = new ArrayList<>();
            direction1Times.add(times[0]);
            direction1Times.add(times[1]);
        }
    }
}
