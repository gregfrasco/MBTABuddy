package mbta.mbtabuddy.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mbta.mbtabuddy.R;
import mbta.mbtabuddy.googleMaps.MapManager;
import mbta.mbtabuddy.googleMaps.StationInfoWindowAdapter;
import mbta.mbtabuddy.mbta.Lines;

public class TrackerFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnCameraMoveListener {

    private GoogleMap googleMap;
    private int zoomLevel = 15;
    private MapManager mapManager;
    private boolean subwayView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng marker = new LatLng(42.3132883,-71.1972408);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, zoomLevel));
        this.mapManager = new MapManager(this.getActivity().getBaseContext(),this.googleMap);
        this.googleMap.setInfoWindowAdapter(new StationInfoWindowAdapter());
        this.mapManager.drawAllLines(true);
        this.mapManager.addAllStations(true);
        this.googleMap.setOnCameraMoveListener(this);
        this.subwayView = true;
        this.googleMap.setMinZoomPreference(11f);
    }

    @Override
    public void onCameraMove() {
        int newZoomLevel = (int) this.googleMap.getCameraPosition().zoom;
        if (newZoomLevel != this.zoomLevel) {
            this.zoomLevel = newZoomLevel;
            if(zoomLevel > 13) {
                if(!subwayView){
                    this.subwayView = true;
                    this.googleMap.clear();
                    this.mapManager.drawAllLines(true);
                    this.mapManager.addAllStations(true);
                }
            } else {
                if(subwayView){
                    this.subwayView = false;
                    this.googleMap.clear();
                    this.mapManager.drawAllLines(false);
                    this.mapManager.addAllStations(false);
                }
            }


        }
    }
}