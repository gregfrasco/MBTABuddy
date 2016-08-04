package mbta.mbtabuddy.fragments;


import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mbta.mbtabuddy.R;
import mbta.mbtabuddy.util.ZoomImageVIew;

public class MBTAmap extends Fragment {


    public MBTAmap() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mbtamap, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ZoomImageVIew mapView = (ZoomImageVIew)this.getActivity().findViewById(R.id.mapView);
        mapView.setImageDrawable(getResources().getDrawable(R.drawable.subway_spider));
        mapView.setMaxZoom(5);
    }
}
