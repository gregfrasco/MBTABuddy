package mbta.mbtabuddy.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import mbta.mbtabuddy.R;
import mbta.mbtabuddy.util.ZoomImageView;

public class MBTAmap extends Fragment implements TabHost.OnTabChangeListener {

    private TabHost tabHost;
    private ZoomImageView subwayImageView;
    private ZoomImageView railImageView;
    private ProgressDialog progressDialog;


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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Map...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        this.subwayImageView = (ZoomImageView)this.getActivity().findViewById(R.id.subwayView);
        this.subwayImageView.setImageDrawable(getResources().getDrawable(R.drawable.subway_spider));
        this.subwayImageView.setMaxZoom(5);
        this.subwayImageView.setMinZoom(1);

        this.railImageView = (ZoomImageView)this.getActivity().findViewById(R.id.railView);
        this.railImageView.setImageDrawable(getResources().getDrawable(R.drawable.rail_spider));
        this.railImageView.setMaxZoom(5);
        this.railImageView.setMinZoom(1);

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

        progressDialog.dismiss();
    }

    @Override
    public void onTabChanged(String s) {
        switch (s){
            case "Subway":
                progressDialog.show();
                this.subwayImageView.setZoom(1);
                this.subwayImageView.setVisibility(View.VISIBLE);
                this.railImageView.setVisibility(View.GONE);
                progressDialog.dismiss();
                break;
            case "Commuter Rail":
                progressDialog.show();
                this.railImageView.setZoom(1);
                this.subwayImageView.setVisibility(View.GONE);
                this.railImageView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                break;
        }
    }
}
