package gmap;

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
    private String vehicleNum;

    public TrainMarker(MBTARoutes.Routes route, Marker marker, String VehicleNum)
    {
        vehicleNum = VehicleNum;
        mapMarker = marker;
        trainRoute = route;

        //Get out bitmapDescriptor (icon bmp) based on the color of the train
        BitmapDescriptor bmp = GetIcon();
        marker.setIcon(bmp);
    }

    public String GetVehicleNum()
    {
        return vehicleNum;
    }

    public Marker GetMarker()
    {
        return mapMarker;
    }

    public MBTARoutes.Routes GetRouteEnum()
    {
        return trainRoute;
    }

    private BitmapDescriptor GetIcon()
    {
        if(trainRoute == MBTARoutes.Routes.Blue_Line)
        {
            //Set marker icon to blue
            return BitmapDescriptorFactory.fromResource(R.drawable.ic_blue);
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
