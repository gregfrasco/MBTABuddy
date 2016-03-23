package gmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import DataManagement.IconHelper;
import mbta.Line;
import mbta.Lines;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class TrainMarker
{
    private Marker mapMarker;
    private Line trainRoute;
    private String vehicleNum;
    private String setStationId;
    private Context context;

    public TrainMarker(Line line, Marker marker, String VehicleNum, Context cont)
    {
        vehicleNum = VehicleNum;
        mapMarker = marker;
        trainRoute = line;
        context = cont;


        //Get out bitmapDescriptor (icon bmp) based on the color of the train
        BitmapDescriptor bmp = GetIcon(trainRoute);
        marker.setIcon(bmp);
    }

    public void SetSetStationId(String statId)
    {
        setStationId = statId;
    }

    public String GetSetStationId()
    {
        return setStationId;
    }

    public String GetVehicleNum()
    {
        return vehicleNum;
    }

    public Line GetLines()
    {
        return trainRoute;
    }

    public Marker GetMarker()
    {
        return mapMarker;
    }

    public Line GetRouteEnum()
    {
        return trainRoute;
    }


    public  BitmapDescriptor GetIcon(Line line) {
        BitmapDescriptor theBmp;
        switch(line.getColor()){
            case Color.RED:
              theBmp = BitmapDescriptorFactory
                       .fromBitmap(IconHelper.drawableToBitmap(context.getResources().getDrawable(R.drawable.ic_red_24dp)));

                return theBmp;
            case Color.GREEN:
               theBmp = BitmapDescriptorFactory
                        .fromBitmap(IconHelper.drawableToBitmap(context.getResources().getDrawable(R.drawable.ic_green_24dp)));

                return theBmp;
            case Color.BLUE:
                theBmp = BitmapDescriptorFactory
                        .fromBitmap(IconHelper.drawableToBitmap(context.getResources().getDrawable(R.drawable.ic_blue_24dp)));

                return theBmp;
            default:
                theBmp = BitmapDescriptorFactory
                        .fromBitmap(IconHelper.drawableToBitmap(context.getResources().getDrawable(R.drawable.ic_orange_24dp)));

                return theBmp;
        }
    }

    public static int GetIconResource(Line line) {
        switch(line.getColor()){
            case Color.RED:
                return R.drawable.ic_red_24dp;
            case Color.GREEN:
                return R.drawable.ic_green_24dp;
            case Color.BLUE:
                return R.drawable.ic_blue_24dp;
            default:
                return R.drawable.ic_orange_24dp;
        }
    }


}
