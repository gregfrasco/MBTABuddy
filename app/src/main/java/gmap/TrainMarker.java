package gmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import mbta.Lines;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class TrainMarker
{
    private Marker mapMarker;
    private Lines trainRoute;
    private String vehicleNum;

    public TrainMarker(Lines lines, Marker marker, String VehicleNum)
    {
        vehicleNum = VehicleNum;
        mapMarker = marker;
        trainRoute = lines;

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

    public Lines GetRouteEnum()
    {
        return trainRoute;
    }

    public BitmapDescriptor GetIcon() {
        switch(trainRoute){
            case Red_Line:
            case Mattapan_Line:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_red);
            case Orange_Line:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_orange);
            case Green_Line_B:
            case Green_Line_C:
            case Green_Line_D:
            case Green_Line_E:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
            case Blue_Line:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_blue);
            default:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_green);
        }
    }

    public int GetIconResource() {
        switch(trainRoute){
            case Red_Line:
            case Mattapan_Line:
                return R.drawable.ic_red;
            case Orange_Line:
                return R.drawable.ic_orange;
            case Green_Line_B:
            case Green_Line_C:
            case Green_Line_D:
            case Green_Line_E:
                return R.drawable.ic_green;
            case Blue_Line:
                return R.drawable.ic_blue;
            default:
                return R.drawable.ic_green;
        }
    }


}
