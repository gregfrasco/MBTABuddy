package mbta.mbtabuddy.Fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mbta.mbtabuddy.R;


public class FavoritesFragment extends Fragment {

    public FavoritesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancetate){
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
}