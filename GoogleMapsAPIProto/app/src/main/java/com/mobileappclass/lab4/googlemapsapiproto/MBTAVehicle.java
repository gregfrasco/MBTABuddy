package com.mobileappclass.lab4.googlemapsapiproto;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Marker;

import java.util.Date;

/**
 * Created by cruzj6 on 2/5/2016.
 */
public abstract class MBTAVehicle {
    protected Date arrivalTime;
    protected double timeRemSecs;
    protected double latitude;
    protected double longitude;

    public abstract BitmapDescriptor getIcon();
}
