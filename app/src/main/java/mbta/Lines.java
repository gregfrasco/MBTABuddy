package mbta;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frascog on 2/17/16.
 */
public class Lines {

    private static Lines instance;
    //Lines
    public final Line RedLine;
    public final Line OrangeLine;
    public final Line GreenLineB;
    public final Line GreenLineC;
    public final Line GreenLineD;
    public final Line GreenLineE;
    public final Line BlueLine;
    public final Line MattapanLine;

    public Lines() {
        this.RedLine       = new Line("Red", Color.RED);
        this.OrangeLine    = new Line("Orange", Color.rgb(255,140,0));
        this.GreenLineB    = new Line("Green-B", Color.GREEN);
        this.GreenLineC    = new Line("Green-C", Color.GREEN);
        this.GreenLineD    = new Line("Green-D", Color.GREEN);
        this.GreenLineE    = new Line("Green-E", Color.GREEN);
        this.BlueLine      = new Line("Blue", Color.BLUE);
        this.MattapanLine  = new Line("Mattapan", Color.RED);
    }

    public static Lines getInstance(){
        if(instance == null){
            instance = new Lines();
        }
        return instance;
    }

    public List<Line> values() {
        new Lines();
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

    public Line getLine(String routeId) {
        for(Line line: this.values()){
            if(line.getLineID().equals(routeId)){
                return line;
            }
        }
        return null;
    }
}
