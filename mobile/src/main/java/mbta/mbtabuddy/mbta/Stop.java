package mbta.mbtabuddy.mbta;

/**
 * Created by frascog on 3/22/16.
 */
public class Stop {

    private String stopID;
    private String destination;
    private String direction;
    private String lineID;
    private int color;

    public Stop(String stopID, String destination, String direction, String lineID) {
        this.stopID = stopID;
        this.destination = destination;
        this.direction = direction;
        this.lineID = lineID;
        for(Line line:Lines.getInstance().values()){
            if(line.getLineID().equals(lineID)){
                this.color = line.getColor();
                break;
            }
        }
    }

    public Stop(String stopID) {
        this.stopID = stopID;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
