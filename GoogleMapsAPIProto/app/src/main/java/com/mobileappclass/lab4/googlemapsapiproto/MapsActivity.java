package com.mobileappclass.lab4.googlemapsapiproto;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Get bitmap descriptor for the icon image resource
        BitmapDescriptor markIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_cookie);

        GMapsRouteRequest("42.3374786,-71.0953609", "42.3731106,-71.1224075", "transit");

        //Create a LatLng container for the location for the icon
        //Maybe we can get from MBTA API?
        LatLng winthrop = new LatLng(42.3804809,-70.9827367);

        //Add the marker to map
        Marker winthropMark = mMap.addMarker(new MarkerOptions()
                .position(winthrop)
                .title("Winthrop Marker")
                .icon(markIcon));

        //Procedure to move camera to a position
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(winthrop, 17));

        //Can dynamically change marker position
        LatLng newLoc = new LatLng(42.3804809, -70.9627367);
        winthropMark.setPosition(newLoc);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLoc, 13));

        //All marker "clicks" come here, each marker doesn't get its own listener
        //So need to id them here
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                return false;
            }
        });
    }

    void GMapsRouteRequest(String origin, String dest)
    {
        GMapsRouteRequest(origin, dest, null);
    }

    //This method shows how to make a request for a JSON object
    //containing information on routes from the Google Maps API
    void GMapsRouteRequest(String origin, String dest, String travelType)
    {
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
        fullRequest += "key=" + getString(R.string.ServerKey);

        final String response;
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
                //Utilize the jSON object
                String stat = "";
                JSONArray testArray = new JSONArray();
                JSONObject routesObject = new JSONObject();
                JSONArray legsArray = new JSONArray();
                JSONArray stepsArray = new JSONArray();
                JSONArray subStepsArray = new JSONArray();

                try {
                    //Routes: The array of routes requested
                    testArray =  response.getJSONArray("routes");

                    //Get the first route (should be only)
                    routesObject = testArray.getJSONObject(0);

                    //Gets the array of legs of the route (ie. if there is waypoints,
                    //there will be multiple legs, otherwise just one element, with
                    //steps for that leg
                    legsArray = routesObject.getJSONArray("legs"); //Get the legs

                    //Get the first leg for testing sake (We may have more depending on how we do
                    //this, and get the array of steps from that leg
                    stepsArray = legsArray.getJSONObject(0).getJSONArray("steps");

                    //Now iterate through the steps
                    for (int i = 0; i < stepsArray.length(); i++)
                    {
                        //Get each object key of this step, if it has transit_details key,
                        //we should get that transit info
                        Iterator<String> thisStepKeys = stepsArray.getJSONObject(i).keys();
                        while(thisStepKeys.hasNext())
                        {
                            String key = thisStepKeys.next();
                            if(key.equals("transit_details"))
                            {
                                //Log the transit details
                                Log.v("MBTAProto", stepsArray.getJSONObject(i).getJSONObject(key).toString());
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullRequest, null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });

        RequestQueue reqQ = Volley.newRequestQueue(getApplicationContext());
        reqQ.add(request);
    }
}
