package mbta;

/**
 * Created by frascog on 2/17/16.
 */
public enum LineType {

    Tram,
    Subway,
    Commuter_Rail,
    Bus,
    Boat;

    @Override
    public String toString() {
        return super.toString().replaceAll("_"," ");
    }
}
