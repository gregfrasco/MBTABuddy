package gmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/25/2016.
 */
public class MBTAStaticMapFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancetate) {

        View view = inflater.inflate(R.layout.mbta_static_map, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.mbta_map_image);
        try {
            //Get the url object for the image source
            URL url = new URL("http://www.mbta.com/images/subway-spider.jpg");
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setDoInput(true);
            connect.connect();

            InputStream is = connect.getInputStream();
            Bitmap mapImage = BitmapFactory.decodeStream(is);

            img.setImageBitmap(mapImage);
        }
        catch(Exception e)
        {
            Log.e("MBTAStaticMap", "SomeError With onCreateView()");
        }

        return view;
    }
}
