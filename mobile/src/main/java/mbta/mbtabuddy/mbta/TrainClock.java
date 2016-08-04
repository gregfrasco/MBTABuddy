package mbta.mbtabuddy.mbta;

import android.os.CountDownTimer;

import mbta.mbtabuddy.StationActivity;


/**
 * Created by frascog on 3/24/16.
 */
public class TrainClock extends CountDownTimer {

    private StationActivity stationActivity;

    public TrainClock(long millisInFuture, long countDownInterval, StationActivity stationActivity) {
        super(millisInFuture, countDownInterval);
        this.stationActivity = stationActivity;
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        //stationActivity.moveTrains();
    }
}
