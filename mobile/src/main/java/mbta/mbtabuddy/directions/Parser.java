package mbta.mbtabuddy.directions;

import java.util.List;

public interface Parser {
    List<Route> parse() throws RouteException;
}