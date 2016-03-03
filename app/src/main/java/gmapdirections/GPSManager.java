package gmapdirections;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import gmap.MapManager;

/**
 * Created by cruzj6 on 2/17/2016.
 *
 * =============================================================
 * NOTE: TO TEST THIS CLASS ON THE EMULATOR USE TELNET.
 * I.E -> Telnet localhost 5554 to connect to emulator terminal
 * and use command "geo fix <long> <lat>" to emulate gps
 * ============================================================
 */
public class GPSManager implements LocationListener {

    private static GPSManager instance;
    private LocationManager locationManager;
    private Context myContext;
    private MapManager mapManager;

    public static GPSManager getInstance() {
        if (instance == null) {
            instance = new GPSManager();
        }
        return instance;
    }

    public void LogLastLocation() {
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //TODO
        }
        Location lastLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.v("GPSManager", lastLoc.getLatitude() + " " + lastLoc.getLongitude());
    }

    public void InitLocationManager(Context context, LocationManager _locationManager) {
        myContext = context;
        locationManager = _locationManager;
        mapManager = new MapManager(myContext);
    }

    /*
     * See Note at top of GPSManager file for how to test GPS on emulator
     */
    @Override
    public void onLocationChanged(Location location) {
        //Log.v("GPSManager", "Device GPS Location Changed to: " + location.getLatitude() + " " + location.getLongitude());
        mapManager.moveMyLocationMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v("GPSManager", "Provider Status changed: Provider =" + provider + " status=" + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.v("GPSManager", "provider enabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.v("GPSManager", "provider disabled: " + provider);
    }
}
