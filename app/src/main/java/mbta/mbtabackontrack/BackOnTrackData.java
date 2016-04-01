package mbta.mbtabackontrack;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cruzj6 on 3/29/2016.
 */
public class BackOnTrackData {

    private JSONObject overMetrics;
    private static BackOnTrackData instance;

    public enum TransitTypes{
        BUS,
        COMMUTER,
        RAIL;

        @Override
        public String toString() {
            switch (this){
                case BUS:
                    return "Bus";
                case COMMUTER:
                    return "Commuter";
                case RAIL:
                    return "Rail";
                default:
                    return "";
            }
        }
    }

    public static BackOnTrackData getInstance()
    {
        if(instance == null)
          instance = new BackOnTrackData();
        return instance;
    }

    public BackOnTrackData() {
        RequestThread reqRunnable = new RequestThread("http://www.mbtabackontrack.com/performance/performance.php?metric=overview");

        Thread reqThread =
                new Thread(reqRunnable);

        reqThread.start();
        try {
            reqThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String resp = reqRunnable.getResponse();
        JSONObject respJSON = null;
        try {
            respJSON = new JSONObject(resp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject metricsOvr = null;
        try {
            metricsOvr = respJSON.getJSONObject("dashboard")
                    .getJSONArray("metricCategories")
                    .getJSONObject(0)
                    .getJSONArray("overviewMetrics").getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        overMetrics = metricsOvr;
    }

    private String[] getStatOrder() throws InterruptedException, JSONException {
        String[] statOrder = new String[3];

        JSONObject catOrder = overMetrics.getJSONObject("categoryOrder");

        String busIndex = (String)catOrder.getString("Bus");
        String comRail = (String)catOrder.getString("Commuter");
        String theT = (String)catOrder.getString("Rail");

        statOrder[Integer.parseInt(busIndex)] = TransitTypes.BUS.toString();
        statOrder[Integer.parseInt(comRail)] = TransitTypes.COMMUTER.toString();
        statOrder[Integer.parseInt(theT)] = TransitTypes.RAIL.toString();

        return statOrder;
    }

    public HashMap<String, String> getTargetForTransType() throws JSONException
    {
        HashMap<String, String > stats = new HashMap<>();
        JSONArray series = overMetrics.getJSONArray("series");

        //Get the reliability object
        JSONObject reliability = new JSONObject();
        for(int i=0; i<series.length(); i++)
        {
            if(series.getJSONObject(i).getString("name").equals("Target"))
            {
                reliability = series.getJSONObject(i);
            }
        }

        //Get the order the stats are shown in
        String[] statOrder = {"NoDATA"};
        try {
            statOrder = getStatOrder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Build the hashmap
        JSONArray targetData = reliability.getJSONArray("data");
        for(int i=0; i<statOrder.length; i++)
        {
            stats.put(statOrder[i], targetData.getString(i));
        }

        return stats;
    }

    public HashMap<String,String> getReliabilityForTransType() throws JSONException {
        HashMap<String, String> stats = new HashMap<>();
        JSONArray series = overMetrics.getJSONArray("series");

        //Get the reliability object
        JSONObject reliability = new JSONObject();
        for(int i=0; i<series.length(); i++)
        {
           if(series.getJSONObject(i).getString("name").equals("Actual"))
           {
               reliability = series.getJSONObject(i);
           }
        }

        //Get the order the stats are shown in
        String[] statOrder = {"NoDATA"};
        try {
            statOrder = getStatOrder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Build the hashmap
        JSONArray reliabilityData = reliability.getJSONArray("data");
        for(int i=0; i<statOrder.length; i++)
        {
            stats.put(statOrder[i], reliabilityData.getString(i));
        }

        return stats;
    }

    private class RequestThread implements Runnable {
        private String fullRequest;
        private String response;

        RequestThread(String request) {
            fullRequest = request;
        }

        @Override
        public void run() {
            try {
                response = "";
                URL url = null;
                try {
                    url = new URL(fullRequest);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                InputStream inStream = null;  // throws an IOException
                try {
                    inStream = url.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
                try {
                    String readline = "";
                    while ((readline = br.readLine()) != null) {
                        response += readline;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e("BackOnTrackData", e.getMessage());
            }
        }
        public String getResponse() {
            return response;
        }
    }
}
