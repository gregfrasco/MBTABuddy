package mbta;

import java.util.List;

import mbta.mbtaAPI.MBTARoutes;
import mbta.mbtaAPI.Route;
import mbta.mbtabuddy.R;

/**
 * Created by frascog on 2/17/16.
 */
public class Line {

    private Lines lines;
    private String lineID;
    private String lineName;
    private List<Station> stations;
    private LineType type;
    private Station terminalStation1;
    private Station terminalStation2;

    //TODO
    public Line(Route route) {
        MBTA mbta = MBTA.getInstance();
        this.lineID = route.getRouteId();
        this.lineName = route.getRouteName();
        this.type = route.getLineType();
        this.stations = mbta.getStationsByLine(this);
        Station[] stations = mbta.getTerminalStations(this);
        this.setTerminalStation1(stations[0]);
        this.setTerminalStation2(stations[1]);
    }

    public Line(Lines lines){
        Line line = MBTA.getInstance().getLine(lines);
        this.lines = lines;
        this.setLineID(line.getLineID());
        this.setLineName(line.getLineName());
        this.setType(line.getType());
        this.setStations(line.getStations());
        this.setTerminalStation1(line.getTerminalStation1());
        this.setTerminalStation2(line.getTerminalStation2());
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    public Station getTerminalStation2() {
        return terminalStation2;
    }

    public void setTerminalStation2(Station terminalStation2) {
        this.terminalStation2 = terminalStation2;
    }

    public Station getTerminalStation1() {
        return terminalStation1;
    }

    public void setTerminalStation1(Station terminalStation1) {
        this.terminalStation1 = terminalStation1;
    }

    public int getColor() {
        if(this.getLineName().equals("Red Line")){
            return R.color.mbta_Red;
        } else if(this.getLineName().equals("Orange Line")){
            return R.color.mbta_Orange;
        }
        return R.color.mbta_Green;
    }
}
