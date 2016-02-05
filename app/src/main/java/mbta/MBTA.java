package mbta;

import android.util.Log;

import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Greg on 2016-02-04.
 */
public class MBTA{

    private static MBTA instance;
    private final String mbtaAPI = "http://realtime.mbta.com/developer/api/v2/";
    private final String apiKey = "?api_key=RpDBj89zSU6aOljozJLfpg";
    private final String format = "&format=json";
    private String results;
    private List<Route> routes;

    public static MBTA getInstance(){
        if(instance == null){
           instance = new MBTA();
        }
        return instance;
    }

    private MBTA(){

    }

    public List<Route> getRoutes(){
        if(routes == null) {
            String apiResult = run(mbtaAPI + "routes" + apiKey + format);
            List<Route> routes = new ArrayList<Route>();
            Gson gson = new Gson();
            JSON json = gson.fromJson(apiResult, JSON.class);
            for (Mode mode : json.getMode()) {
                routes.addAll(mode.getRoute());
            }
            this.routes = routes;
        }
        return routes;
    }

    public List<ParentStation> getStopsByRoute(Route route){
        String apiResult = run(mbtaAPI + "stopsbyroute" + apiKey + "&route="+ route.getRouteId() + format);
        Gson gson = new Gson();
        StopsByRoute stopsByRoute = gson.fromJson(apiResult, StopsByRoute.class);
        HashMap<String,ParentStation>parentStationMap = new HashMap<String,ParentStation>();
        for(Direction direction : stopsByRoute.getDirection()){
            for(Stop stop: direction.getStop()){
                if(!parentStationMap.containsKey(stop.getParentStationName())){
                    parentStationMap.put(stop.getParentStationName(),new ParentStation(stop));
                }else{
                    parentStationMap.get(stop.getParentStationName()).addStop(stop);
                }
            }
        }
        return new ArrayList<ParentStation>((Collection<? extends ParentStation>) parentStationMap.values());
    }

    public String run(final String query) {
        final Thread thread = new Thread() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    URL url = new URL(query);
                    is = url.openStream();  // throws an IOException
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    MBTA.this.results = br.readLine();
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
