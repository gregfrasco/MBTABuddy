package mbta;

/**
 * Created by frascog on 2/4/16.
 */
public class RouteType {

    private Type type;

    public RouteType(String type){
        this.type = this.getType(type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        Subway,
        Commuter_Rail,
        Bus,
        Boat;
    }

    public Type getType(String mode){
        Type type = null;
        switch (mode){
            case "Subway":
                type = Type.Subway;
                break;
            case "Communter Rail":
                type = Type.Commuter_Rail;
                break;
            case "Bus":
                type = Type.Bus;
                break;
            case "Boat":
                type = Type.Boat;
                break;
        }
        return type;
    }
}