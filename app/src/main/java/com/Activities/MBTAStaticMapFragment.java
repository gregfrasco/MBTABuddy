package com.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        try {
            final ImageView img = (ImageView) view.findViewById(R.id.mbta_map_image);
            final TextView link = (TextView) view.findViewById(R.id.imageLink);

            Thread mapThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //If this doesn't work out we just use a local static image, idea is
                        //this image may be updated some day
                        final String mbtaMapImageURL = "http://www.mbta.com/images/subway_spider.jpg";

                        //Get the url object for the image source
                        URL url = new URL(mbtaMapImageURL);
                        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                        connect.setDoInput(true);
                        connect.connect();

                        //Open the input stream and stream to bitmap factory
                        InputStream is = connect.getInputStream();
                        Bitmap mapImage = BitmapFactory.decodeStream(is);

                        img.setImageBitmap(mapImage);
                        link.setText("Image From: " + mbtaMapImageURL);
                    } catch (Exception e) {
                        Log.e("MBTAStaticMapFragment", "Error in getting map bitmap thread, using default image");
                        img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.subway_spider));
                    }
                }
            });

            mapThread.start();
            mapThread.join();
        }
        catch(Exception e)
        {
            Log.e("MBTAStaticMapFragment", "Error in onCreateView()");
        }

        return view;
    }
}
