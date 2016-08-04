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
import mbta.mbtabuddy.mbta.Lines;

public class TrackerFragment extends Fragment implements OnMapReadyCallback {

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
        LatLng marker = new LatLng(42.3132883,-71.1972408);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15));
        googleMap.addMarker(new MarkerOptions().title("Hello Google Maps!").position(marker));
        MapManager mapManager = new MapManager(this.getActivity().getBaseContext(),googleMap);
        mapManager.drawAllLines();
        mapManager.addAllStations();
    }
}