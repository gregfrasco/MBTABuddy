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
import java.util.HashMap;
import java.util.List;

import mbta.mbtaAPI.Alert;
import mbta.mbtaAPI.AlertsByRoute;
import mbta.mbtaAPI.Direction;
import mbta.mbtaAPI.Mode;
import mbta.mbtaAPI.PredictionsByRoute;
import mbta.mbtaAPI.PredictionsByStop;
import mbta.mbtaAPI.PredictionsByTrip;
import mbta.mbtaAPI.Route;
import mbta.mbtaAPI.Routes;
import mbta.mbtaAPI.RoutesByStop;
import mbta.mbtaAPI.ScheduleByRoute;
import mbta.mbtaAPI.ScheduleByStop;
import mbta.mbtaAPI.ScheduleByTrip;
import mbta.mbtaAPI.Stop;
import mbta.mbtaAPI.StopsByLocation;
import mbta.mbtaAPI.StopsByRoute;
import mbta.mbtaAPI.Trip;
import mbta.mbtaAPI.Vehicle;
import mbta.mbtaAPI.VehiclesByRoute;

/**
 * Created by Greg on 2016-02-04.
 */
public class MBTA{

    private static MBTA instance;
    private final String mbtaAPI = "http://realtime.mbta.com/developer/api/v2/";
    private final String apiKey = "?api_key=lxzqGfe9OUWHBR9elGklBg";
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

    public Line getLine(String lines){
        for (Line line:Lines.getInstance().values()){
            if(line.getLineID().equals(lines)){
                return line;
            }
        }
        return null;
    }

    private List<Route> getRoutes(){
        if(routes == null) {
            String apiResult = run(mbtaAPI + "routes" + apiKey + format);
            Gson gson = new Gson();
            Routes routes = gson.fromJson(apiResult, Routes.class);
            this.routes = routes.getRoutes();
        }
        return this.routes;
    }

    public List<Station> getStationsByLine(Line line){
        List<Stop> stops = this.getStopsByRoute(line);
        List<Station> stations = new ArrayList<Station>();
        for(Stop stop:  stops){
            stations.add(new Station(stop));
        }
        return stations;
    }

    private List<Stop> getStopsByRoute(Line line){
        String apiResult = run(mbtaAPI + "stopsbyroute" + apiKey + "&route=" + line.getLineID() + format);
        Gson gson = new Gson();
        StopsByRoute stopsByRoute = gson.fromJson(apiResult, StopsByRoute.class);
        HashMap<String,Stop> stops = new HashMap<String,Stop>();
        for(Direction direction: stopsByRoute.getDirection()){
            for(Stop stop: direction.getStop()){
                stops.put(stop.getStopId(),stop);
            }
        }
        return new ArrayList<Stop>(stops.values());
    }

    public Station[] getTerminalStations(Line line) {
        Station[] stations = new Station[2];
        String apiResult = run(mbtaAPI + "stopsbyroute" + apiKey + "&route=" + line.getLineID() + format);
        Gson gson = new Gson();
        StopsByRoute stopsByRoute = gson.fromJson(apiResult, StopsByRoute.class);
        for(int i = 0;i < stopsByRoute.getDirection().size();i++){
            stations[i] = new Station(stopsByRoute.getDirection().get(i).getStop().get(0));
        }
        return stations;
    }

    public List<Station> getStopsByLocation(double lat, double lon){
        String apiResult = run(mbtaAPI + "stopsbylocation" + apiKey + "&lat=" + lat + "&lon=" + lon + format);
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
                    MBTA.this.results = "";
                    String readLine;
                    while((readLine = br.readLine()) != null)
                    {
                        MBTA.this.results += readLine;
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
        String apiResult = run(mbtaAPI + "routesbystop" + apiKey + "&stop="+ station.getStationID() + format);
        Gson gson = new Gson();
        RoutesByStop routesByStop = gson.fromJson(apiResult, RoutesByStop.class);
        return routesByStop.getRoutes();
    }

    public List<Line> getRoutesByStop(String stationID, String stationName) {
        String apiResult = run(mbtaAPI + "routesbystop" + apiKey + "&stop="+ stationID + format);
        Gson gson = new Gson();
        RoutesByStop routesByStop = gson.fromJson(apiResult, RoutesByStop.class);
        List<Line> lines = new ArrayList<Line>();
        for(Route route: routesByStop.getRoutes()){
            lines.add(this.getLine(route.getRouteId()));
        }
        return lines;
    }

    public List<Trip> getScheduleByStop(Station station) {
        String apiResult = run(mbtaAPI + "schedulebystop" + apiKey + "&stop="+ station.getStationID() + format);
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

    public VehiclesByRoute GetVehiclesByRouteItem(String routeId)
    {
        String apiResult = run(mbtaAPI + "vehiclesbyroute" + apiKey + "&route=" + routeId + format);
        Gson gson = new Gson();
        VehiclesByRoute vehiclesByRoute = gson.fromJson(apiResult, VehiclesByRoute.class);
        return vehiclesByRoute;
    }

    public HashMap<String, String> getRemTime(String routeID, String VehicleId, String stopId)
    {
       /* String apiResult = run(mbtaAPI + "routesbystop" + apiKey + "&stop="+ stationName + format);
        Gson gson = new Gson();
        RoutesByStop routesByStop = gson.fromJson(apiResult, RoutesByStop.class);*/
        HashMap<String, String> remTimeForTripId = new HashMap<>();

        String apiResult = run(mbtaAPI + "predictionsbystop" + apiKey + "&stop=" + stopId + format);
        Gson gson = new Gson();
        PredictionsByStop routesByStop = gson.fromJson(apiResult, PredictionsByStop.class);

        for(Mode mode : routesByStop.getMode())
        {
            for(Route route : mode.getRoute())
            {
                for(Direction direction : route.getDirection())
                {
                    for(Trip trip : direction.getTrip()) {
                        Vehicle vehicle = trip.getVehicle();
                        if(vehicle != null) {
                            if (vehicle.getVehicleId().equals(VehicleId))
                            {
                                Log.v("MBTA", trip.getPreAway());
                            }
                        }
                    }
                }
            }
        }

        return remTimeForTripId;
    }

    public List<Integer> getPredictionsByStop(String stopID){
        String apiResult = run(mbtaAPI + "predictionsbystop" + apiKey + "&stop=" + stopID + format);
        Gson gson = new Gson();
        PredictionsByStop predictionsByStop = gson.fromJson(apiResult, PredictionsByStop.class);
        List<Integer> times = new ArrayList<Integer>();
        List<Trip> trips = new ArrayList<Trip>();
        if(predictionsByStop.getMode().size() > 0){
            if(predictionsByStop.getMode().get(0).getRoute().size() > 0){
                if(predictionsByStop.getMode().get(0).getRoute().get(0).getDirection().size() > 0) {
                     trips = predictionsByStop.getMode().get(0).getRoute().get(0).getDirection().get(0).getTrip();
                }
            }
        }
        for(Trip trip: trips){
            times.add(Integer.parseInt(trip.getPreAway()));
        }
        return times;
    }

    private PredictionsByRoute getPredictionsByRoute(String lineID){
        String apiResult = run(mbtaAPI + "predictionsbyroute" + apiKey + "&route="+ lineID + format);
        Gson gson = new Gson();
        return gson.fromJson(apiResult, PredictionsByRoute.class);

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

    public List<Vehicle> getVehiclesByRoute(Line line) {
        String apiResult = run(mbtaAPI + "vehiclesbyroute" + apiKey + "&route=" + line.getLineID() + format);
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

    public Station getStopByID(String stationID) {
        for(Line line : Lines.getInstance().values()){
            for(Station station:line.getStations()){
                if(station.getStationID().equals(stationID)){
                    return station;
                }
            }
        }
        return null;
    }

    public List<mbta.Stop> getAllStops(Station station, List<Line> lines) {
        List<mbta.Stop> stopIDs = new ArrayList<mbta.Stop>();
        for(Line line:lines){
            for(Stop stop:this.getStopsByRoute(line)){
                if(station.getStationName().equals(stop.getParentStationName())){
                    stopIDs.add(new mbta.Stop(stop.getStopId()));
                }
            }
        }
        return stopIDs;
    }
}
