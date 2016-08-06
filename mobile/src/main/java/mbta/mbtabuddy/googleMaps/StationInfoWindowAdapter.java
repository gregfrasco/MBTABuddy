package mbta.mbtabuddy.googleMaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import mbta.mbtabuddy.MainActivity;
import mbta.mbtabuddy.R;

/**
 * Created by frascog on 8/6/16.
 */

public class StationInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater layoutInflater = (LayoutInflater)MainActivity.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.info_window_station, null);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.line_list);
        String[] lineIDs = marker.getSnippet().split(", ");
        for(String lineID: lineIDs){
            ImageView image = new ImageView(MainActivity.context);
            switch(lineID){
                case "Red":
                    image.setImageDrawable(MainActivity.context.getResources().getDrawable(R.drawable.red));
                    break;
                case "Orange":
                    image.setImageDrawable(MainActivity.context.getResources().getDrawable(R.drawable.orange));
                    break;
                case "Green-B":
                    image.setImageDrawable(MainActivity.context.getResources().getDrawable(R.drawable.greenb));
                    break;
                case "Green-C":
                    image.setImageDrawable(MainActivity.context.getResources().getDrawable(R.drawable.greenc));
                    break;
                case "Green-D":
                    image.setImageDrawable(MainActivity.context.getResources().getDrawable(R.drawable.greend));
                    break;
                case "Green-E":
                    image.setImageDrawable(MainActivity.context.getResources().getDrawable(R.drawable.greene));
                    break;
                case "Blue":
                    image.setImageDrawable(MainActivity.context.getResources().getDrawable(R.drawable.blue));
                    break;
            }
            linearLayout.addView(image);
        }
        TextView stationName = (TextView) v.findViewById(R.id.station_name);
        stationName.setText(marker.getTitle());
        return v;
    }
}
