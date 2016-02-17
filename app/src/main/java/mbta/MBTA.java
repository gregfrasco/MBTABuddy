package mbta;

import android.util.Log;

import com.google.gson.Gson;

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
    private final String alerts = "&include_access_alerts=true&include_service_alerts=true";
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

    //TODO Keep
    public Line getLine(Lines lines){
        MBTARoutes mbtaRoutes = MBTARoutes.getInstance();
        return new Line(this.getRoute(mbtaRoutes.getLineID(lines)));
    }

    //TODO Clean this method
    //TODO Keep
    public List<Route> getRoutes(){
        if(routes == null) {
            String apiResult = run(mbtaAPI + "routes" + apiKey + format);
            List<Route> routes = new ArrayList<Route>();
            Gson gson = new Gson();
            RoutesJSON json = gson.fromJson(apiResult, RoutesJSON.class);
            for (Mode mode : json.getMode()) {
                List<Route> setRoute = mode.getRoute();
                for(Route route: setRoute){
                    route.setRouteType(new RouteType(mode.getModeName()));
                }
                routes.addAll(setRoute);
            }
            this.routes = routes;
        }
        return routes;
    }

    //TODO This
    public List<Station> getStationsByLine(Line line){
        return null;
    }

    public List<ParentStation> getStopsByRoute(Route route){
        String apiResult = run(mbtaAPI + "stopsbyroute" + apiKey + "&route=" + route.getRouteId() + format);
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

    public List<Station> getStopsByLocation(double lat, double lon){
        String apiResult = run(mbtaAPI + "stopsbylocation" + apiKey + "&lat="+ lat + "&lon="+ lon + format);
        Gson gson = new Gson();
        StopsByLocation stopsByLocation = gson.fromJson(apiResult, StopsByLocation.class);
        List<Station> stations = new ArrayList<Station>();
        for (Stop stop : stopsByLocation.getStop()){
            stations.add(new Station(stop));
        }
        return stations;
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

    public Route getRoute(String ID){
        List<Route> routes = this.getRoutes();
        for(Route route: routes){
            if(route.getRouteId().equals(ID)){
                return route;
            }
        }
        return null;
    }

    public List<Route> getRoutesByStop(Station station) {
        String apiResult = run(mbtaAPI + "routesbystop" + apiKey + "&stop="+ station.getStopID() + format);
        Gson gson = new Gson();
        RoutesByStop routesByStop = gson.fromJson(apiResult, RoutesByStop.class);
        return routesByStop.getRoutes();
    }

    public List<Trip> getScheduleByStop(Station station) {
        String apiResult = run(mbtaAPI + "schedulebystop" + apiKey + "&stop="+ station.getStopID() + format);
        Gson gson = new Gson();
        ScheduleByStop scheduleByStop = gson.fromJson(apiResult, ScheduleByStop.class);
        List<Trip> trips = new ArrayList<Trip>();
        for(Mode mode :scheduleByStop.getMode()){
            for(Route route : mode.getRoute()){
                for(Direction direction: route.getDirection()){
                    trips.addAll(direction.getTrip());
                }
            }
        }
        return trips;
    }

    public List<Trip> getScheduleByRoute(Route route){
        String apiResult = run(mbtaAPI + "schedulebyroute" + apiKey + "&route="+ route.getRouteId() + format);
        Gson gson = new Gson();
        ScheduleByRoute scheduleByRoute = gson.fromJson(apiResult, ScheduleByRoute.class);
        List<Trip> trips = new ArrayList<Trip>();
        for(Direction direction: scheduleByRoute.getDirection()){
            trips.addAll(direction.getTrip());
        }
        return trips;
    }

    public List<Trip> getScheduleByTrip(Trip trip){
        String apiResult = run(mbtaAPI + "schedulebytrip" + apiKey + "&trip="+ trip.getTripId() + format);
        Gson gson = new Gson();
        ScheduleByTrip scheduleByTrip = gson.fromJson(apiResult, ScheduleByTrip.class);
        List<Trip> trips = new ArrayList<Trip>();
        for(Direction direction: scheduleByTrip.getDirection()){
            trips.addAll(direction.getTrip());
        }
        return trips;
    }

    //TODO Return Something
    private void getPredictionsByStop(Station station){
        String apiResult = run(mbtaAPI + "predictionsbystop" + apiKey + "&stop="+ station.getStopID() + format);
        Gson gson = new Gson();
        PredictionsByStop predictionsByStop = gson.fromJson(apiResult, PredictionsByStop.class);
        Log.v("MBTA",predictionsByStop.getMode().get(0).getRoute().get(0).getDirection().get(0).getTrip().get(0).getVehicle().getVehicleBearing());

    }

    //TODO Return Something
    private void getPredictionsByRoute(Route route){
        String apiResult = run(mbtaAPI + "predictionsbyroute" + apiKey + "&route="+ route.getRouteId() + format);
        Gson gson = new Gson();
        PredictionsByRoute predictionsByRoute = gson.fromJson(apiResult, PredictionsByRoute.class);
        for(Direction direction: predictionsByRoute.getDirection()){
            for(Trip trip: direction.getTrip()){
                Log.v("MBTA",trip + " mph " + trip.getPreAway());
            }
        }
    }

    public List<Vehicle> getVehiclesByRoute(Route route){
        String apiResult = run(mbtaAPI + "vehiclesbyroute" + apiKey + "&route="+ route.getRouteId() + format);
        Gson gson = new Gson();
        VehiclesByRoute vehiclesByRoute = gson.fromJson(apiResult, VehiclesByRoute.class);
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        for(Direction direction: vehiclesByRoute.getDirection()){
            for(Trip trip: direction.getTrip()){
                vehicles.add(trip.getVehicle());
            }
        }
        return vehicles;
    }

    public List<Stop> getPredictionsByTrip(Trip trip) {
        String apiResult = run(mbtaAPI + "predictionsbytrip" + apiKey + "&trip="+ trip.getTripId() + format);
        Gson gson = new Gson();
        PredictionsByTrip predictionsByTrip = gson.fromJson(apiResult, PredictionsByTrip.class);
        return predictionsByTrip.getStop();
    }

    private List<Alert> getAlerts(){
        String apiResult = run(mbtaAPI + "predictionsbytrip" + apiKey + format);
        Gson gson = new Gson();
        //PredictionsByTrip predictionsByTrip = gson.fromJson(apiResult, PredictionsByTrip.class);
        //return predictionsByTrip.getStop();
        return null;
    }

    public List<Alert> getAlertsByRoute(Route route){
        String apiResult = run(mbtaAPI + "alertsbyroute" + apiKey + "&route="+ route.getRouteId() + alerts + format);
        Gson gson = new Gson();
        AlertsByRoute alertsByRoute = gson.fromJson(apiResult, AlertsByRoute.class);
        return alertsByRoute.getAlerts();
    }

    public List<Alert> getAlertsByStop(Stop stop){
        String apiResult = run(mbtaAPI + "alertsbystop" + apiKey + "&route="+ stop.getStopId() + alerts + format);
        Gson gson = new Gson();
        AlertsByRoute alertsByRoute = gson.fromJson(apiResult, AlertsByRoute.class);
        return alertsByRoute.getAlerts();
    }
}
