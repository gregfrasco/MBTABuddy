package gmap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import mbta.MBTA;
import mbta.mbtaAPI.Direction;
import mbta.mbtaAPI.Trip;
import mbta.mbtaAPI.Vehicle;
import mbta.mbtaAPI.VehiclesByRoute;
import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class TrainInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final View window;
    private MapManager mapManager;
    private final Context cont;
    private String stationId;

    TrainInfoWindow(Context context, MapManager mManager)
    {
        cont = context;
        mapManager = mManager;
        LayoutInflater inf = (LayoutInflater)cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        window = inf.inflate(R.layout.train_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker)
    {
        //Set our title of the info window
        TextView title = (TextView) window.findViewById(R.id.title);

        //Get our train marker which we can use for getting information
        TextView snippetText = (TextView) window.findViewById(R.id.ETA  );
        TrainMarker tMarker = mapManager.getTrainMarkerFromId(marker.getId());

        ImageView image = (ImageView) window.findViewById(R.id.mImage);
        //TODO: change icon based on train (route Enum maybe)
        image.setImageBitmap(BitmapFactory.decodeResource(cont.getResources(), TrainMarker.getIconResource(tMarker.getLines())));

        //Get our Vehicles on the route
        MBTA mbta = MBTA.getInstance();
        VehiclesByRoute vehiclesByRoute = mbta.GetVehiclesByRouteItem(tMarker.getLines().getLineID());

        //Look for the corresponding vehicle
        for(Direction direction : vehiclesByRoute.getDirection()) {
            for(Trip trip : direction.getTrip()) {
                if(trip.getVehicle().getVehicleId().equals(tMarker.getVehicleNum())) {
                    //Utilize the Headsign data of the train
                    Vehicle vehicle = trip.getVehicle();
                    title.setText(trip.getTripHeadsign());

                    //Set our text to indicate the train's direction and departure time
                    TextView directionText = (TextView)window.findViewById(R.id.Direction);
                    directionText.setText(trip.getTripName());

                    //If the optional speed parameter is retrieved show the user
                    if(vehicle.getVehicleSpeed() != null) {
                        snippetText.setText("Speed: " + vehicle.getVehicleSpeed());
                    }
                }
            }
        }

        return window;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
