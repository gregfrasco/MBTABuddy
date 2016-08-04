package mbta.mbtabuddy.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mbta.mbtabuddy.R;


public class TrackerFragment extends Fragment {

    public TrackerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancetate){
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }
}
