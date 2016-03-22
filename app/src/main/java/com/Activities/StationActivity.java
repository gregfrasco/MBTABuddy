package com.Activities;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataManagement.FavoritesDataContainer;
import DataManagement.IconHelper;
import directions.AbstractRouting;
import directions.Route;
import directions.RouteException;
import directions.Routing;
import directions.RoutingListener;
import DataManagement.DataStorageManager;
import gmap.MapManager;
import gmap.StationMarker;
import gmap.TrainMarker;
import mbta.ArrivalTime;
import mbta.CountdownTimer;
import mbta.CountdownTimerListener;
import mbta.Line;
import mbta.Lines;
import mbta.MBTA;
import mbta.Station;
import mbta.mbtaAPI.Vehicle;
import mbta.mbtabuddy.R;

public class StationActivity extends FragmentActivity implements OnMapReadyCallback {

    private DataStorageManager dataManager;
    private MapManager mapManager;
    private Station station;
    private List<TrainMarker> trainMarkers = new ArrayList<TrainMarker>();
    private List<StationMarker> stationMarkers = new ArrayList<StationMarker>();
    private HashMap<String,TextView> countDownClocks;

    private TextView station1;
    private TextView station2;
    private TextView station1time;
    private TextView station2time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.stationMap);
        mapFragment.getMapAsync(this);

        //Get our id of the station from the intent
        Bundle bundle = getIntent().getExtras();
        String stationID = bundle.getString("ID");
        this.station = MBTA.getInstance().getStopByID(stationID);
        setTitle(station.getStationName());
        this.mapManager = new MapManager(this);

        station1 = (TextView) findViewById(R.id.station1);
        station2 = (TextView) findViewById(R.id.station2);
        station1time = (TextView) findViewById(R.id.station1min);
        station2time = (TextView) findViewById(R.id.station2min);
        station1.setText(this.station.getLine().get(0).getTerminalStation1().getStationName());
        station2.setText(this.station.getLine().get(0).getTerminalStation2().getStationName());

        TextView name = (TextView) findViewById(R.id.stationName);
        name.setText(this.station.getStationName());
        LinearLayout stationHeader = (LinearLayout) findViewById(R.id.stationHeader);
        stationHeader.setBackgroundColor(this.station.getLine().get(0).getColor());
        initCountDownClicks();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapManager.setMap(googleMap);
        new LoadStation(StationActivity.this,this.station,this.mapManager).execute();
    }

    public void addFavoriteStation(View view) {
        if(dataManager == null)
            dataManager = DataStorageManager.getInstance();
        dataManager.SetContext(this); //TODO: Move to onResume()? or other method?

        Drawable filledStarDrawable = getResources().getDrawable(R.drawable.ic_star_24dp);
        ImageButton favoritesButton = (ImageButton) findViewById(R.id.favoriteButton);
        favoritesButton.setImageBitmap(IconHelper.drawableToBitmap(filledStarDrawable));

        //Save it
        dataManager.SaveStationFavorite(station.getStationID(), station.getStationName());
    }
    //TODO Make Countdown Clock Fragment
    private void initCountDownClicks(){
        this.countDownClocks = new HashMap<String,TextView>();
        int count = 0;
        for(String stopID: this.station.getStopIDs()){
            if(count == 0) {
                this.countDownClocks.put(stopID, this.station1time);
                count += 1;
            } else if(count == 1) {
                this.countDownClocks.put(stopID,this.station2time);
                count += 1;
            } else {
                break;
            }
            ArrivalTime arrivalTime = this.station.getArrivalTimes(stopID);
            CountDownClock countDownClock = new CountDownClock(arrivalTime.getFirstTime(),1000,stopID);
            countDownClock.start();
            updateCountdownClock(stopID,arrivalTime.getFirstTime());
        }
    }

    private void updateCountdownClock(String stopID,int timeInMilliseconds){
        int time = timeInMilliseconds/1000;
        if(time <= 60){
            this.countDownClocks.get(stopID).setText("ARR");
            this.countDownClocks.get(stopID).invalidate();
        } else {
            this.countDownClocks.get(stopID).setText((time/60 + 1) + " min");
            this.countDownClocks.get(stopID).invalidate();
        }
    }

    private void startNewTimer(String stopID) {
        ArrivalTime arrivalTime = this.station.getArrivalTimes(stopID);
        CountDownClock countDownClock = new CountDownClock(arrivalTime.getFirstTime(),1000,stopID);
        this.updateCountdownClock(stopID,arrivalTime.getFirstTime());
        countDownClock.start();
    }

    private class LoadStation extends AsyncTask<String, Integer, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(StationActivity.this);
        private StationActivity activity;
        private MapManager mapManager;
        private Station station;

        public LoadStation(StationActivity activity,Station station,MapManager mapManager){
            this.activity = activity;
            this.mapManager = mapManager;
            this.station = station;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Loading Station...");
            this.dialog.setIndeterminate(true);
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                DataStorageManager.getInstance().SetContext(activity);
                List<FavoritesDataContainer> favs = (List<FavoritesDataContainer>)
                        DataStorageManager.getInstance().LoadUserData(DataStorageManager.UserDataTypes.FAVORITES_DATA);

                //Check if this is already a favorite, if it is give it the full star icon
                for(FavoritesDataContainer fav : favs)
                {
                    if(fav.favName.equals(station.getStationName()))
                    {
                        final Drawable filledStarDrawable = getResources().getDrawable(R.drawable.ic_star_24dp);
                        final ImageButton favoritesButton = (ImageButton) findViewById(R.id.favoriteButton);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                favoritesButton.setImageBitmap(IconHelper.drawableToBitmap(filledStarDrawable));
                            }
                        });
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(this.dialog.isShowing()){
                this.dialog.dismiss();
            }
            this.mapManager.drawTrainLines(station.getLine());
            this.mapManager.drawStations(station.getLine());
            this.mapManager.addTrains(station.getLine(), station);
            this.mapManager.zoomToStationMarker(station.getStationID(), 16);
        }
    }

    private class CountDownClock extends CountDownTimer{

        private String stopID;

        public CountDownClock(long millisInFuture, long countDownInterval,String stopID) {
            super(millisInFuture, countDownInterval);
            this.stopID = stopID;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if((millisUntilFinished % 975) <= 250) {
                StationActivity.this.updateCountdownClock(stopID, (int) millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            StationActivity.this.startNewTimer(stopID);
        }
    }
}

