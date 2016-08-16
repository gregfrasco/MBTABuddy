package mbta.mbtabuddy.test;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import mbta.mbtabuddy.mbta.Line;
import mbta.mbtabuddy.mbta.MBTA;
import mbta.mbtabuddy.mbta.Station;
import mbta.mbtabuddy.mbta.mbtaAPI.Stop;
import mbta.mbtabuddy.mbta.mbtaAPI.StopsByRoute;

/**
 * Created by frascog on 8/16/16.
 */

public class getStations {

    private static String ID = "CR-Lowell";
    private static final String mbtaAPI = "http://realtime.mbta.com/developer/api/v2/";
    private static final String apiKey = "?api_key=RpDBj89zSU6aOljozJLfpg";
    private static final String format = "&format=json";
    private static String results;

    public static void main(String[] args) {
        String apiResult = run(mbtaAPI + "stopsbyroute" + apiKey + "&route=" + ID + format);
        Gson gson = new Gson();
        StopsByRoute stopsByRoute = gson.fromJson(apiResult, StopsByRoute.class);
        for(Stop stop:stopsByRoute.getDirection().get(0).getStop()){
            String message = stop.getStopId() +",";
            message += stop.getStopName() + ",";
            message += stop.getStopLat() + ",";
            message += stop.getStopLon() + ",";
            message += ID;
            System.out.println(message);
        }
    }

    private static String run(final String query) {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    URL url = new URL(query);
                    //apiCount();
                    is = url.openStream();  // throws an IOException
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    results = "";
                    String readLine;
                    while ((readLine = br.readLine()) != null) {
                        results += readLine;
                    }
                } catch (MalformedURLException mue) {
                    mue.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException ioe) {
                        // nothing to see here
                    }
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return results;
    }
}
