package com.mbtabuddy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

            //Set the text fields
            commuterReli.setText(reliData.get(BackOnTrackData.TransitTypes.COMMUTER.toString())
                    + "/" + targetData.get(BackOnTrackData.TransitTypes.COMMUTER.toString()));
            trainReli
                    .setText(reliData.get(BackOnTrackData.TransitTypes.RAIL.toString())
                            + "/" + targetData.get(BackOnTrackData.TransitTypes.RAIL.toString()));
            busReli
                    .setText(reliData.get(BackOnTrackData.TransitTypes.BUS.toString())
                            + "/" + targetData.get(BackOnTrackData.TransitTypes.BUS.toString()));
        }
        catch (Exception e)
        {

        }

        return retView;
    }
}
