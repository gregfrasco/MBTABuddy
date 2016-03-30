package com.mbtabuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/25/2016.
 */
public class MBTAStaticMapFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancetate) {
        View view = inflater.inflate(R.layout.mbta_static_map, container, false);
        return view;
    }
}
