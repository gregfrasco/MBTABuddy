package gmap;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import mbta.MBTARoutes;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class TrainMarker
{
    private Marker mapMarker;
    private MBTARoutes.Routes trainRoute;
    private String tripNum;

    public TrainMarker(MBTARoutes.Routes route, Marker marker, String TripNum)
    {
        tripNum = TripNum;
        mapMarker = marker;
        trainRoute = route;
        marker.setIcon(GetIcon());
    }

    public String GetTripNum()
    {
        return tripNum;
    }

    public Marker GetMarker()
    {
        return mapMarker;
    }

    public MBTARoutes.Routes GetRouteEnum()
    {
        return trainRoute;
    }

    public BitmapDescriptor GetIcon()
    {
        if(trainRoute == MBTARoutes.Routes.Blue_Line)
        {
            //Set marker icon to blue

            BitmapDescriptor bmp = BitmapDescriptorFactory.fromResource(R.drawable.ic_blue);
            return bmp;
        }
        else if(trainRoute== MBTARoutes.Routes.Red_Line)
        {
            //set red icon
            return BitmapDescriptorFactory.fromResource(R.drawable.ic_red);
        }
        else if(trainRoute == MBTARoutes.Routes.Green_Line_B || trainRoute == MBTARoutes.Routes.Green_Line_C ||
                trainRoute == MBTARoutes.Routes.Green_Line_D || trainRoute == MBTARoutes.Routes.Green_Line_E)
        {
            //set green icon
            return BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
        }
        else if(trainRoute == MBTARoutes.Routes.Orange_Line)
        {
            //set orange icon
            return BitmapDescriptorFactory.fromResource(R.drawable.ic_orange);
        }
        else //Default icon
            return BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
    }


}
