package gmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/10/2016.
 */
public class TrainInfoWindow implements GoogleMap.InfoWindowAdapter {

    private final View window;
    private MapManager mapManager;
    private final Context cont;

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
        title.setText(marker.getTitle());

        //Get our train marker which we can use for getting information
        TextView snippet = (TextView) window.findViewById(R.id.ETA  );
        TrainMarker tMarker = mapManager.getTrainMarkerFromId(marker.getId());

        ImageView image = (ImageView) window.findViewById(R.id.mImage);
        //TODO: change icon based on train (route Enum maybe)
        image.setImageBitmap(BitmapFactory.decodeResource(cont.getResources(), tMarker.GetIconResource()));

        //TODO: for now just set the content to the color....
        snippet.setText(tMarker.GetRouteEnum().toString());
        return window;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
