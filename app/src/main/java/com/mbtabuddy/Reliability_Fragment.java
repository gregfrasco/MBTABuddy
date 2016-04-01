package com.mbtabuddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

import DataManagement.LoadingDialogManager;
import mbta.mbtabackontrack.BackOnTrackData;
import mbta.mbtabuddy.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reliability_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reliability_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reliability_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LoadingDialogManager.getInstance().DismissLoading();
        View retView = inflater.inflate(R.layout.mbta_reliability, container, false);
        TextView commuterReli = (TextView) retView.findViewById(R.id.commuter_reliability);
        TextView trainReli = (TextView) retView.findViewById(R.id.rail_reliability);
        TextView busReli = (TextView) retView.findViewById(R.id.bus_reliability);

        try {
            //Get the data
            HashMap<String, String> reliData = BackOnTrackData.getInstance().getReliabilityForTransType();
            HashMap<String, String> targetData = BackOnTrackData.getInstance().getTargetForTransType();

            //Get commuter rail data as int
            int comReli = getIntPercentage(reliData, BackOnTrackData.TransitTypes.COMMUTER);
            int comTar = getIntPercentage(targetData, BackOnTrackData.TransitTypes.COMMUTER);

            //Subway data
            int trainReliPer = getIntPercentage(reliData, BackOnTrackData.TransitTypes.RAIL);
            int trainTarPer = getIntPercentage(targetData, BackOnTrackData.TransitTypes.RAIL);

            //Bus data
            int busReliPer = getIntPercentage(reliData, BackOnTrackData.TransitTypes.BUS);
            int busTarPer = getIntPercentage(targetData, BackOnTrackData.TransitTypes.BUS);

            //Set the text fields
            commuterReli.setText(comReli + "%"
                    + "/" + comTar + "%");
            trainReli
                    .setText(trainReliPer + "%"
                            + "/" + trainTarPer + "%");
            busReli
                    .setText(busReliPer + "%"
                            + "/" + busTarPer + "%");
        }
        catch (Exception e)
        {
            Log.e("ReliabilityFragment", "Error: " + e.getMessage());
        }

        //Set up button for seeing additional statistics (Open Back on track site)
        Button openMBTASiteBtn = (Button) retView.findViewById(R.id.mbta_site_open_btn);
        openMBTASiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Give the extra for the URL and create intent for opening webViewAct
                Intent opnWebIntent = new Intent(getActivity(), WebViewActivity.class);
                opnWebIntent.putExtra("url", "http://www.mbtabackontrack.com/performance/index.html#/home");
                startActivity(opnWebIntent);
            }
        });

        return retView;
    }

    private int getIntPercentage(HashMap<String, String> data,BackOnTrackData.TransitTypes type)
    {
        return (int)(Double.parseDouble(data.get(type.toString())) * 100);
    }
}
