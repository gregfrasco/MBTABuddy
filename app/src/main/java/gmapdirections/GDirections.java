package gmapdirections;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;

import mbta.mbtabuddy.R;

/**
 * Created by cruzj6 on 2/10/2016.
 * This class is for working with the Google Directions API if we
 * decide to do routing
 */
public class GDirections {

    private Context cont;
    static GDirections instance;

    public static GDirections getInstance()
    {
        if(instance == null)
            instance = new GDirections();
        return instance;
    }

    public RouteInfoContainer GetRouteInfo(LatLng start, LatLng dest)
    {
        //Create our new container and make a query for the directions information
        RouteInfoContainer newRouteInfo = new RouteInfoContainer();
        String response = RequestDirectionInfo(start.latitude + "," + start.longitude,
                dest.latitude + "," + dest.longitude,"transit");
        try {
            //Extract the duration of each leg, of the most optimal route and add up
            double totalTimeSecs = 0;
            JSONObject responseObj = new JSONObject(response);
            JSONObject idealRoute = responseObj.getJSONArray("routes").getJSONObject(0);
            JSONArray routeLegs = idealRoute.getJSONArray("legs");

            //Should only be one leg, because we are not using waypoints
            totalTimeSecs = routeLegs.getJSONObject(0).getJSONObject("duration").getDouble("value");

            //Next we want the ETA
            String etaString =
                    routeLegs.getJSONObject(0).getJSONObject("arrival_time").getString("text");

            //Add it to our container
            newRouteInfo.ETAString = etaString;
            newRouteInfo.travelTime = totalTimeSecs;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newRouteInfo;
    }

    /**
     * Usage to get transit information out of the Google Directions API
     */
    public void Test()
    {
        String response = RequestDirectionInfo("42.3499239,-71.0782962", "42.3731106,-71.1224075", "transit");
        try {
            JSONObject responseObj = new JSONObject(response);

            //Routes: The array of routes requested
            JSONArray routesArray =  responseObj.getJSONArray("routes");

            //Get the first route (Recommended one)
            JSONObject routesObject = routesArray.getJSONObject(0);

            //This will get us all of the steps that involve transit
            //So not walking, biking etc.
            String stepsJSONString = GetTransitStepsJSON(routesObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setContext(Context context)
    {
        cont = context;
    }

    /**
     *
     * @param routesObject
     *      JSON object from routes array from Google Directions API response (So one of the Routes)
     * @return
     *      A string JSON object of just the steps that include public transit
     */
    private String GetTransitStepsJSON(JSONObject routesObject)
    {
        String transitSteps = "";

        try {
        //Gets the array of legs of the route (ie. if there is waypoints,
        //there will be multiple legs, otherwise just one element, with
        //steps for that leg
        JSONArray legsArray = routesObject.getJSONArray("legs"); //Get the legs

        //Get the first leg for testing sake (We may have more depending on how we do
        //this, and get the array of steps from that leg
        JSONArray stepsArray = legsArray.getJSONObject(0).getJSONArray("steps");

        //Now iterate through the steps
        for (int i = 0; i < stepsArray.length(); i++)
        {
            //Get the handle on he step we are on, iterating through the steps
            JSONObject thisStep = null;

                thisStep = stepsArray.getJSONObject(i);

            //Iterate through each key in this step's JSON Object to find out if it
            //is a transit step (can probably filter this better)
            Iterator<String> thisStepKeys = thisStep.keys();
            while(thisStepKeys.hasNext())
            {
                //If it has a transit_details key we can look at the transit
                //departure time etc.
                String key = thisStepKeys.next();
                if(key.equals("transit_details"))
                {
                    transitSteps += thisStep.toString();
                }
            }
        }
        }
        catch (JSONException e) {
            e.printStackTrace();
            transitSteps = "MBTABuddy GetTransitStepsJSON() Error";
        }

        return transitSteps;
    }

    /**
     *
     * @param origin
     * @param dest
     * @param travelType
     *      Use "transit" for public transit directions
     * @return
     *      The JSON object as a string containing the full Google Maps Direction API response
     */
    private String RequestDirectionInfo(String origin, String dest, String travelType) {
        String gMapsApiReq = "https://maps.googleapis.com/maps/api/directions/";
        String requestType = "json";

        //Parameters to be used in requesting directions from GMaps web API
        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put("origin", origin);
        params.put("destination", dest);
        if(travelType != null)
        {
            params.put("mode", travelType);
        }

        //Build our full request string
        String fullRequest = gMapsApiReq;
        fullRequest += requestType + "?";

        int i = 1;
        int max = params.keySet().size();
        for(String paramKey : params.keySet())
        {
            String value = params.get(paramKey);
            fullRequest += paramKey + "=" + value + "&";
        }

        fullRequest += "transit_mode=rail&";
        fullRequest += "key=" + cont.getString(R.string.google_server_key);

        RequestThread req = new RequestThread(fullRequest);
        Thread reqThread = new Thread(req);

        reqThread.start();
        try {
            reqThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return req.getResponse();
    }
}

class RequestThread implements Runnable
{
    private String fullRequest;
    private String response;

    RequestThread(String request)
    {
        fullRequest = request;
    }

    @Override
    public void run()
    {
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
            while((readline = br.readLine()) != null)
            {
                response += readline;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponse()
    {
        return response;
    }
}
