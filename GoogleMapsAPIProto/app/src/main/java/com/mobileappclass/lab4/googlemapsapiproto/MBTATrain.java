package com.mobileappclass.lab4.googlemapsapiproto;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by cruzj6 on 2/5/2016.
 */
public class MBTATrain extends MBTAVehicle {

    private MBTALineEnum line;

    public MBTATrain(double lat, double lon, MBTALineEnum _line)
    {
        line = _line;
        longitude = lon;
        latitude = lat;
    }

    @Override
    public BitmapDescriptor getIcon() {
        //Get based on line
        return BitmapDescriptorFactory.fromResource(R.drawable.cookie);
    }
}
