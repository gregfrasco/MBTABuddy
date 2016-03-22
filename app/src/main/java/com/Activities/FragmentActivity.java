package com.Activities;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import DataManagement.LoadingDialogManager;
import mbta.mbtabuddy.R;

public class FragmentActivity extends AppCompatActivity {

    final String TAG = "FragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Intent myIntent = getIntent();
        int position = (int)myIntent.getExtras().get("switchNum");

        Fragment newFragment = null;
        //Show loading box
        LoadingDialogManager.getInstance().ShowLoading(this);

        //Check if we have a network connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected())
        {
            newFragment = new NoConnectionFragment();
        }
        else {
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

        FragmentManager fm = (FragmentManager) getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.full_fragment, newFragment).commit();
        LoadingDialogManager.getInstance().DismissLoading();

    }
}
