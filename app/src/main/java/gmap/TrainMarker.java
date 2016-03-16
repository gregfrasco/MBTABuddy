package gmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

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

    public TrainMarker(Line line, Marker marker, String VehicleNum)
    {
        vehicleNum = VehicleNum;
        mapMarker = marker;
        trainRoute = line;


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


    public static BitmapDescriptor GetIcon(Line line) {
        switch(line.getColor()){
            case Color.RED:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_red);
            case Color.GREEN:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
            case Color.BLUE:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_blue);
            default:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_orange);
        }
    }

    public static int GetIconResource(Line line) {
        switch(line.getColor()){
            case Color.RED:
                return R.drawable.ic_red;
            case Color.GREEN:
                return R.drawable.ic_green;
            case Color.BLUE:
                return R.drawable.ic_blue;
            default:
                return R.drawable.ic_orange;
        }
    }


}
