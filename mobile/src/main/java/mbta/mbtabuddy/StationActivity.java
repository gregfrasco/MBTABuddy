package mbta.mbtabuddy;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import mbta.mbtabuddy.googleMaps.MapManager;
import mbta.mbtabuddy.mbta.CountDownClock;
import mbta.mbtabuddy.mbta.MBTA;
import mbta.mbtabuddy.mbta.Station;
import mbta.mbtabuddy.mbta.Stop;
import mbta.mbtabuddy.mbta.TrainClock;

public class StationActivity extends FragmentActivity implements OnMapReadyCallback {

    //private DataStorageManager dataManager;
    private MapManager mapManager;
    private Station station;
    //private List<TrainMarker> trainMarkers = new ArrayList<TrainMarker>();
    //private List<StationMarker> stationMarkers = new ArrayList<StationMarker>();

    private TextView station1;
    private TextView station2;
    private TextView station1time;
    private TextView station2time;
    private LinearLayout stationHeader;
    private Stop firstStop = null;
    private CountDownClock countDownClock1;
    private CountDownClock countDownClock2;
    private TrainClock trainClock;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        //LoadingDialogManager.getInstance().ShowLoading(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.stationMap);
        mapFragment.getMapAsync(this);

        //Get our id of the station from the intent
        Bundle bundle = getIntent().getExtras();
        String stationID = bundle.getString("ID");
        this.station = MBTA.getInstance().getStopByID(stationID);
        this.mapManager = new MapManager(this,null);
        //this.mapManager.setStationClickable(false);

        station1 = (TextView) findViewById(R.id.station1);
        station2 = (TextView) findViewById(R.id.station2);
        station1time = (TextView) findViewById(R.id.station1min);
        station2time = (TextView) findViewById(R.id.station2min);
        //station1.setText(this.station.getLine().get(0).getTerminalStation1().getStationName());
        //station2.setText(this.station.getLine().get(0).getTerminalStation2().getStationName());

        //Set our view components
        TextView name = (TextView) findViewById(R.id.stationName);
        name.setText(this.station.getStationName().toUpperCase());
        name.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf"));
        stationHeader = (LinearLayout) findViewById(R.id.stationHeader);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(this.station.getLine().get(0).getColor());
        }
        //Tabs
        final TabHost tabs=(TabHost)findViewById(R.id.tabHost);
        tabs.setup();
        tabs.clearAllTabs();
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
                //updateTrains(stop);
                //startNewTimer(stop);
            }
        });
        stationHeader.setBackgroundColor(firstStop.getColor());
        tabs.setCurrentTab(0);
        //Clocks
        //initCountDownClicks();
        //Favorites
        //List<FavoritesDataContainer> favs = (List<FavoritesDataContainer>)DataStorageManager.getInstance().LoadUserData(DataStorageManager.UserDataTypes.FAVORITES_DATA);
/*
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
        */
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
        //new LoadStation(StationActivity.this,this.station,this.mapManager).execute();
        //trainClock = new TrainClock(15,1000,this);
        trainClock.start();
    }

    public void openDirectionsApp(View view)
    {
        //Get current location info
        //Location currentLoc =  GPSManager.getInstance().getCurrentLoc();
        /*
        if(currentLoc != null) {
            //For our URI for getting directions
            String uri = String.format("http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f&mode=walking",
                    currentLoc.getLatitude(),
                    GPSManager.getInstance().getCurrentLoc().getLongitude(),
                    station.getLatLan().latitude,
                    station.getLatLan().longitude);

            //Log what is going to be used
            Log.v("StationActivity", "Directions Requested, URI Used: " + uri);

            //Action view intent so we open external app
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(Intent.createChooser(intent, "Select a directions app"));
        }
        else
        {
            Dialog d = new Dialog(this);
            d.setTitle("Please Enable Location Services to get Directions");
        }
        */
    }

    public void addFavoriteStation(View view) {
        /*
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
        ArrivalTime arrivalTime = this.station.getArrivalTimes(this.firstStop);
        countDownClock1 = new CountDownClock(arrivalTime.getFirstTime(),1000,this.firstStop,1,this);
        countDownClock2 = new CountDownClock(arrivalTime.getSecondTime(),1000,this.firstStop,2,this);
        updateCountdownClock(this.firstStop, 1, arrivalTime.getFirstTime(), countDownClock1.getPrediction());
        updateCountdownClock(this.firstStop, 2, arrivalTime.getSecondTime(), countDownClock2.getPrediction());
        countDownClock1.start();
        countDownClock2.start();
    }

    public void updateCountdownClock(Stop stop, int row, int timeInMilliseconds, boolean prediction){
        if(row == 1){
            this.station1.setText(stop.getDestination());
            this.station1time.setText(formatTime(timeInMilliseconds,prediction));
        } else {
            this.station2.setText(stop.getDestination());
            this.station2time.setText(formatTime(timeInMilliseconds,prediction));
        }
    }

    private String formatTime(int timeInMilliseconds,boolean prediction) {
        if(prediction) {
            if ((timeInMilliseconds / 1000) < 15) {
                return "ARR";
            } else {
                return ((timeInMilliseconds / 1000 / 60) + 1) + " min";
            }
        }else {
            return "No info";
        }
    }

    public void startNewTimer(Stop stop) {
        countDownClock1.cancel();
        countDownClock2.cancel();
        ArrivalTime arrivalTime = this.station.getArrivalTimes(stop);
        countDownClock1 = new CountDownClock(arrivalTime.getFirstTime(),1000,stop,1,this);
        countDownClock2 = new CountDownClock(arrivalTime.getSecondTime(),1000,stop,2,this);
        this.updateCountdownClock(stop,1,arrivalTime.getFirstTime(), countDownClock1.getPrediction());
        this.updateCountdownClock(stop,2,arrivalTime.getSecondTime(),countDownClock2.getPrediction());
        countDownClock1.start();
        countDownClock2.start();
    }

    public void moveTrains() {
        List<Trip> trips = MBTA.getInstance().getTripsByRoute(Lines.getInstance().getLine(this.countDownClock1.getStop().getLineID()));
        for(Trip trip: trips){
            if(findTrainMaker(trip,this.trainMarkers)){
                Vehicle vehicle = trip.getVehicle();
                this.mapManager.moveTrainMarker(vehicle.getVehicleId(),vehicle.getLatLng());
            }
        }
        this.trainClock = new TrainClock(60000,1000,this);
        this.trainClock.start();
    }

    private boolean findTrainMaker(Trip trip, List<TrainMarker> trainMarkers) {
        for(TrainMarker trainMarker: trainMarkers){
            if(trainMarker.getMarker().getId().equals(trip.getVehicle().getVehicleId())){
                return true;
            }
        }
        return false;
    }

    private class LoadStation extends AsyncTask<String, Integer, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(StationActivity.this);
        private StationActivity activity;
        private MapManager mapManager;
        private Station station;

        public LoadStation(StationActivity activity, Station station, MapManager mapManager) {
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
            try {
                DataStorageManager.getInstance().SetContext(activity);
                List<FavoritesDataContainer> favs = (List<FavoritesDataContainer>)
                        DataStorageManager.getInstance().LoadUserData(DataStorageManager.UserDataTypes.FAVORITES_DATA);

                //Check if this is already a favorite, if it is give it the full star icon
                for (FavoritesDataContainer fav : favs) {
                    if (fav.favName.equals(station.getStationName())) {
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

            } catch (Exception e) {
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
            this.checkAsmontLine();
            this.mapManager.drawStations(station.getLine());
            updateTrains(firstStop);
            this.mapManager.zoomToStationMarker(station.getStationID(), 16);
        }

        private void checkAsmontLine() {
            for(Stop stop: station.getStopIDs()) {
                if (stop.getDestination().equals("Ashmont") || station.getStationName().equals("Ashmont")) {
                    this.mapManager.drawAshmontLine();
                    break;
                }
            }
        }
    }

    private void updateTrains(Stop stop){
        this.mapManager.removeAllTrains();
        this.mapManager.addTrains(stop.getLineID(), this.station, stop.getStopID());
    }
    */
    }
}

