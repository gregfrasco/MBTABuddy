package mbta;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frascog on 2/17/16.
 */
public class Lines {

    public static Line RedLine = new Line("Red", Color.RED);
    public static Line OrangeLine = new Line("Orange", Color.rgb(255,140,0));
    public static Line GreenLineB = new Line("Green-B", Color.GREEN);
    public static Line GreenLineC = new Line("Green-C", Color.GREEN);
    public static Line GreenLineD = new Line("Green-D", Color.GREEN);
    public static Line GreenLineE = new Line("Green-E", Color.GREEN);
    public static Line BlueLine = new Line("Blue", Color.BLUE);
    public static Line MattapanLine = new Line("Mattapan", Color.RED);

    public static List<Line> values() {
        List<Line> lines = new ArrayList<Line>();
        lines.add(RedLine);
        lines.add(OrangeLine);
        lines.add(GreenLineB);
        lines.add(GreenLineC);
        lines.add(GreenLineD);
        lines.add(GreenLineE);
        lines.add(BlueLine);
        lines.add(MattapanLine);
        return lines;
    }
}
