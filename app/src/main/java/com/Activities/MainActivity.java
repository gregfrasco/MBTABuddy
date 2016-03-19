package com.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import mbta.Lines;
import mbta.mbtabuddy.R;

public class MainActivity extends ActionBarActivity {

    public static Context context;
    static final String TAG = "MainActivity";
    private DrawerLayout drawerMainLayout;
    private ListView drawerList;
    private RelativeLayout drawerRelativeLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String[] drawerLabels;

    //TODO: Store this some other way
    TrackerFragment trackerFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.getApplicationContext(); // to get assets file later;
        boolean isNoConnection = false;
        //Check if there is a network connection
        FragmentManager fm = getSupportFragmentManager();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected() ||
                !(cm.getActiveNetworkInfo().getTypeName().equalsIgnoreCase("WIFI") ||
                        cm.getActiveNetworkInfo().getTypeName().equalsIgnoreCase("MOBILE"))) {
            isNoConnection = true;
        } else {
            Lines.getInstance(); // Init Lines First
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If no connection insert our no connection fragment
        if (isNoConnection) {
            Fragment noConnectFrag = new NoConnectionFragment();
            fm.beginTransaction().replace(R.id.fragmentContent, noConnectFrag).commit();
        }

        //Get handles on all of our drawer elements
        drawerMainLayout = (DrawerLayout) findViewById(R.id.mainLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);
        drawerRelativeLayout = (RelativeLayout) findViewById(R.id.left_drawerLayout);

        //Configure action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Create our drawer toggle for the layout
        drawerToggle = new ActionBarDrawerToggle(this, drawerMainLayout, R.string.drawer_open,
                R.string.drawer_close);
        drawerMainLayout.setDrawerListener(drawerToggle);

        //Sync with state of drawer itself
        drawerToggle.syncState();

        //Get our menu item strings array
        Resources res = getResources();
        drawerLabels = getResources().getStringArray(R.array.drawer_menu_labels);

        //Build list of drawerMenuItems
        List<DrawerMenuItem> drawerMenuItems = new ArrayList<DrawerMenuItem>();
        for (int i = 0; i < drawerLabels.length; i++) {
            //Build our menu item
            //First assign menu item name
            DrawerMenuItem newItem = new DrawerMenuItem();
            newItem.menuItemName = drawerLabels[i];

            //Give appropriate icons
            switch (i) {
                //Map
                case 0:
                    newItem.menuItemResource = R.drawable.ic_public_24dp;
                    break;

                //Favorites
                case 1:
                    newItem.menuItemResource = R.drawable.ic_star_24dp;
                    break;

                //MBTA Static Map
                case 2:
                    newItem.menuItemResource = R.drawable.ic_directions_train_24dp;
                    break;

                //Settings
                case 3:
                    newItem.menuItemResource = R.drawable.ic_settings_24dp;
                    break;

                //default icon
                default:
                    newItem.menuItemResource = R.drawable.ic_forward_24dp;
                    break;
            }

            drawerMenuItems.add(newItem);
        }

        final DrawerMenuItemAdapter drawerMenuAdapter =
                new DrawerMenuItemAdapter(this, 0, drawerMenuItems);

        //Set the adapter
        final MBTADrawerListener drawerListener = new MBTADrawerListener(getSupportFragmentManager(), this);
        drawerList.setAdapter(drawerMenuAdapter);
        drawerMainLayout.setDrawerListener(drawerListener);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Fragment newFragment;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Check if we have a network connection
                ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected()) {
                    newFragment = new NoConnectionFragment();
                } else {
                    switch (position) {
                        //Tracker fragment
                        case 0:
                            Log.v(TAG, "Fragment Switched to Map");
                            setTitle(getResources().getStringArray(R.array.drawer_menu_labels)[0]);
                            newFragment = new TrackerFragment();
                            break;

                        case 1:
                            Log.v(TAG, "Fragment Switched to Favorites");
                            setTitle(getResources().getStringArray(R.array.drawer_menu_labels)[1]);
                            newFragment = new FavoritesFragment();
                            break;

                        case 2:
                            Log.v(TAG, "Fragment Switched to Static Map");
                            setTitle(getResources().getStringArray(R.array.drawer_menu_labels)[2]);
                            newFragment = new MBTAStaticMapFragment();
                            break;

                        default: //Return, do nothing
                            return;
                    }
                }
                //Let it know to switch when the drawer closes
                drawerListener.setNextFrag(newFragment);

                //Close the drawer
                drawerMainLayout.closeDrawers();

            }
        });
        if (savedInstanceState == null) {
            //Check if there is a network connection
            if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected()) {
                Fragment noConnectFrag = new NoConnectionFragment();
                fm.beginTransaction().replace(R.id.fragmentContent, noConnectFrag).commit();
            } else {
                trackerFrag = new TrackerFragment();
                fm.beginTransaction().replace(R.id.fragmentContent, trackerFrag).commit();
                setTitle(drawerLabels[0]);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        //If this is request for location services from the tracker fragment
        if (requestCode == PermissionConstants.LOCATION_TrackerFragment.getValue()) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.v("Tracker", "Location permission granted, hooking up gpsManager");
                trackerFrag.enableLocationManager();

            } else {

                Log.v("Tracker", "didnt make it");
            }
            return;
        }
    }


    /**
     * Class to run the login process in the background while running the
     * loading screen in the main thread
     */

    private class MBTADrawerListener implements DrawerLayout.DrawerListener {
        private android.support.v4.app.FragmentManager fm;
        private android.support.v4.app.Fragment nextFrag;
        private boolean switchNext = false;
        private Context context;

        public MBTADrawerListener(android.support.v4.app.FragmentManager fManager, Context cont) {
            context = cont;
            fm = fManager;
        }

        public void setNextFrag(Fragment nextFragment) {
            nextFrag = nextFragment;
            switchNext = true;
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @Override
        public void onDrawerClosed(View view) {
            final ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Loading...");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();

            new Thread() {

                @Override
                public void run() {
                    if (!pd.isShowing())
                        pd.show();
                    fm.beginTransaction().replace(R.id.fragmentContent, nextFrag).commit();
                    switchNext = false;
                    pd.dismiss();
                }
            }.start();
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }
}
