package com.Activities;

import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import mbta.Line;
import mbta.Lines;
import mbta.MBTA;
import mbta.mbtabuddy.R;

public class MainActivity extends ActionBarActivity {

    //TODO Change MBTARoutes to route

    private DrawerLayout drawerMainLayout;
    private ListView drawerList;
    private RelativeLayout drawerRelativeLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String[] drawerLabels;

    //TODO: Store this some other way
    TrackerFragment trackerFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MBTA mbta = MBTA.getInstance();
        Line redline = mbta.getLine(Lines.Red_Line);

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
        ArrayAdapter<String> drawerMenuAdapter =
                new ArrayAdapter<String>(this, R.layout.drawer_menu_item_layout,drawerLabels);

        //Set the adapter
        drawerList.setAdapter(drawerMenuAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Fragment newFragment;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //Tracker fragment
                    case 0:
                        newFragment = new TrackerFragment();
                        break;

                    default: //Return, do nothing
                        return;
                }
                //Replace the frame with the fragment
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragmentContent, newFragment).commit();
            }
        });
        if(savedInstanceState == null){
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = new TrackerFragment();
            fm.beginTransaction().replace(R.id.fragmentContent, fragment).commit();
            setTitle(drawerLabels[0]);
        }
    }

    private void InitDrawerLayout()
    {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

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

}
