package mbta.mbtabuddy.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import mbta.mbtabuddy.R;
import mbta.mbtabuddy.util.ZoomImageView;

public class MBTAmap extends Fragment implements TabHost.OnTabChangeListener {

    private TabHost tabHost;
    private ZoomImageView zoomImageView;

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
        this.zoomImageView = (ZoomImageView)this.getActivity().findViewById(R.id.mapView);
        this.zoomImageView.setImageDrawable(getResources().getDrawable(R.drawable.subway_spider));
        this.zoomImageView.setMaxZoom(5);

        this.tabHost = (TabHost) getView().findViewById(R.id.tabHost);
        this.tabHost.setup();

        TabHost.TabSpec spec = this.tabHost.newTabSpec("Subway");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Subway");
        this.tabHost.addTab(spec);

        //Tab 2
        spec = this.tabHost.newTabSpec("Commuter Rail");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Commuter Rail");
        this.tabHost.addTab(spec);

        this.tabHost.setOnTabChangedListener(this);

    }

    @Override
    public void onTabChanged(String s) {
        switch (s){
            case "Subway":
                this.zoomImageView.setImageDrawable(getResources().getDrawable(R.drawable.subway_spider));
                this.zoomImageView.setZoom(1);
                break;
            case "Commuter Rail":
                this.zoomImageView.setImageDrawable(getResources().getDrawable(R.drawable.rail_spider));
                this.zoomImageView.setZoom(1);
                break;
        }
    }
}
