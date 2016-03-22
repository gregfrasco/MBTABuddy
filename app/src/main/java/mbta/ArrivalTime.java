package mbta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by frascog on 2/19/16.
 */
public class ArrivalTime {

    private List<Integer> countDownClockTimes;

    public ArrivalTime(String stopID) {
        this.countDownClockTimes = MBTA.getInstance().getPredictionsByStop(stopID);
    }

    public List<Integer> getTimes() {
        return this.countDownClockTimes;
    }

    public Integer getFirstTime() {
        if(countDownClockTimes.size() > 0){
            return this.countDownClockTimes.get(0) * 1000;
        }
        return 60 * 1000; // Check if train info is available in 60 seconds
    }
}
