package gmapdirections;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by cruzj6 on 2/17/2016.
 */
public class GPSManager implements LocationListener {

    private static GPSManager instance;
    private LocationManager locationManager;
    private Context myContext;

    public static GPSManager getInstance()
    {
        if(instance == null)
        {
            instance = new GPSManager();
        }
        return instance;
    }

    public void InitLocationManager(Context context)
    {
        myContext = context;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
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
