package com.Activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataManagement.DataStorageManager;
import DataManagement.FavoritesDataContainer;
import DataManagement.IconHelper;
import DataManagement.LoadingDialogManager;
import DataManagement.StationFavContainer;
import gmap.MapManager;
import gmap.StationMarker;
import gmap.TrainMarker;
import mbta.ArrivalTime;
import mbta.MBTA;
import mbta.Station;
import mbta.Stop;
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
    private LinearLayout stationHeader;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        LoadingDialogManager.getInstance().ShowLoading(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.stationMap);
        mapFragment.getMapAsync(this);

        //Get our id of the station from the intent
        Bundle bundle = getIntent().getExtras();
        String stationID = bundle.getString("ID");
        this.station = MBTA.getInstance().getStopByID(stationID);
        this.mapManager = new MapManager(this);
        this.mapManager.setStationClickable(false);

        station1 = (TextView) findViewById(R.id.station1);
        station2 = (TextView) findViewById(R.id.station2);
        station1time = (TextView) findViewById(R.id.station1min);
        station2time = (TextView) findViewById(R.id.station2min);
        station1.setText(this.station.getLine().get(0).getTerminalStation1().getStationName());
        station2.setText(this.station.getLine().get(0).getTerminalStation2().getStationName());

        //Set our view components
        TextView name = (TextView) findViewById(R.id.stationName);
        name.setText(this.station.getStationName().toUpperCase());
        name.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf"));
        stationHeader = (LinearLayout) findViewById(R.id.stationHeader);
        initCountDownClicks();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(this.station.getLine().get(0).getColor());
        }
        //Tabs
        final TabHost tabs=(TabHost)findViewById(R.id.tabHost);
        tabs.setup();
        tabs.clearAllTabs();
        Stop firstStop = null;
        for(Stop stop: this.station.getStopIDs()){
            if(firstStop == null){
                firstStop = stop;
            }
            TabHost.TabSpec tab = tabs.newTabSpec(stop.getStopID());
            tab.setIndicator(stop.getDestination());
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return StationActivity.this.station1;
                }
            });
            tabs.addTab(tab);
        }
        TabWidget widget = tabs.getTabWidget();
        tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).getBackground().setColorFilter(this.station.getLine().get(0).getColor(), PorterDuff.Mode.MULTIPLY);
        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Stop stop = StationActivity.this.station.getStop(tabId);
                tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).getBackground().setColorFilter(stop.getColor(), PorterDuff.Mode.MULTIPLY);
                stationHeader.setBackgroundColor(stop.getColor());
                StationActivity.this.changeCountDownClocks(stop);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    StationActivity.this.getWindow().setStatusBarColor(stop.getColor());
                }
            }
        });
        stationHeader.setBackgroundColor(firstStop.getColor());
        tabs.setCurrentTab(0);

        //Favorites
        List<FavoritesDataContainer> favs = (List<FavoritesDataContainer>)DataStorageManager.getInstance().LoadUserData(DataStorageManager.UserDataTypes.FAVORITES_DATA);

        for(FavoritesDataContainer fav : favs) {
            if(fav.getClass().equals(StationFavContainer.class)) {
                if (((StationFavContainer)fav).StationId.equals(station.getStationID())) {
                    Drawable filledStarDrawable = getResources().getDrawable(R.drawable.ic_star_24dp);
                    ImageButton favoritesButton = (ImageButton) findViewById(R.id.favoriteButton);
                    favoritesButton.setImageBitmap(IconHelper.drawableToBitmap(filledStarDrawable));
                }
            }
        }
        //We are done loading
        LoadingDialogManager.getInstance().DismissLoading();
    }

    private void changeCountDownClocks(Stop stop) {

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

    private void initCountDownClicks(){
        this.countDownClocks = new HashMap<String,TextView>();
        int count = 0;
        for(Stop stop: this.station.getStopIDs()){
            if(count == 0) {
                this.countDownClocks.put(stop.getStopID(), this.station1time);
                count += 1;
            } else if(count == 1) {
                this.countDownClocks.put(stop.getStopID(),this.station2time);
                count += 1;
            } else {
                break;
            }
            ArrivalTime arrivalTime = this.station.getArrivalTimes(stop.getStopID());
            CountDownClock countDownClock = new CountDownClock(arrivalTime.getFirstTime(),1000,stop.getStopID());
            countDownClock.start();
            updateCountdownClock(stop.getStopID(),arrivalTime.getFirstTime());
        }
    }

    private void updateCountdownClock(String stopID,int timeInMilliseconds){
        int time = timeInMilliseconds/1000;
        if(time <= 15){
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
            this.mapManager.drawTrainLinesByStops(station.getStopIDs());
            this.mapManager.drawStations(station.getLine());
            this.mapManager.addTrains(station.getLine(), station);
            this.mapManager.zoomToStationMarker(station.getStationID(), 16);
        }
    }

    private class CountDownClock extends CountDownTimer{

        private String stopID;
        private int mintue = 1000;

        public CountDownClock(long millisInFuture, long countDownInterval,String stopID) {
            super(millisInFuture, countDownInterval);
            this.stopID = stopID;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(this.mintue > millisUntilFinished/1000/60 || (int) millisUntilFinished/1000 >= 15){
                this.mintue = (int)millisUntilFinished/1000/60;
                StationActivity.this.updateCountdownClock(stopID, (int) millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            StationActivity.this.startNewTimer(stopID);
        }
    }
}

