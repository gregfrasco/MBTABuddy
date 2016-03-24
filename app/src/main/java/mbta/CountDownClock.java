package mbta;

import android.os.CountDownTimer;

import com.Activities.StationActivity;

/**
 * Created by frascog on 3/24/16.
 */
public class CountDownClock extends CountDownTimer {

    private StationActivity activity;
    private Stop stop;
    private int row;
    private int minute = 1000;
    private boolean prediction = true;

    public CountDownClock(long millisInFuture, long countDownInterval,Stop stop,int row,StationActivity activity) {
        super(millisInFuture, countDownInterval);
        this.stop = stop;
        this.row = row;
        if(millisInFuture == 1200000){
            this.prediction = false;
        }
        this.activity = activity;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(this.minute > millisUntilFinished/1000/60 || (int) millisUntilFinished/1000 >= 15){
            this.minute = (int)millisUntilFinished/1000/60;
            activity.updateCountdownClock(stop, row, (int) millisUntilFinished, prediction);
        }
    }

    @Override
    public void onFinish() {
        activity.startNewTimer(stop);
    }

    public boolean getPrediction() {
        return prediction;
    }

    public Stop getStop() {
        return stop;
    }
}
