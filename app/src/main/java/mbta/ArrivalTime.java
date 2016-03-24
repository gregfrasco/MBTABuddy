package mbta;

import java.util.Collections;
import java.util.List;

/**
 * Created by frascog on 2/19/16.
 */
public class ArrivalTime {

    private List<Integer> countDownClockTimes;

    public ArrivalTime(Stop stop) {
        this.countDownClockTimes = MBTA.getInstance().getPredictionsByStop(stop.getStopID());
        Collections.sort(this.countDownClockTimes);
    }

    public List<Integer> getTimes() {
        return this.countDownClockTimes;
    }

    public Integer getFirstTime() {
        if(countDownClockTimes.size() > 0){
            return this.countDownClockTimes.get(0) * 1000;
        }
        return 20 * 60 * 1000; // Check if train info is available in 60 seconds
    }

    public Integer getSecondTime() {
        if(countDownClockTimes.size() > 1){
            return this.countDownClockTimes.get(1) * 1000;
        }
        return 20 * 60 * 1000; // Check if train info is available in 60 seconds
    }
}
