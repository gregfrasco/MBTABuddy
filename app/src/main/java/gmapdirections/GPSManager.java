package gmapdirections;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.Activities.PermissionConstants;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by cruzj6 on 2/17/2016.
 */
public class GPSManager implements LocationListener {

    private static GPSManager instance;
    private LocationManager locationManager;
    private Context myContext;

    public static GPSManager getInstance() {
        if (instance == null) {
            instance = new GPSManager();
        }
        return instance;
    }

    public void InitLocationManager(Context context, GoogleMap mMap) {
        myContext = context;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        //TODO: http://javapapers.com/android/get-current-location-in-android/

    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
