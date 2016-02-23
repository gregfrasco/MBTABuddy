package mbta;

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
        direction1Times.add(times[0]);
        direction1Times.add(times[1]);
        direction2Times.add(times[2]);
        direction2Times.add(times[3]);
    }
}
