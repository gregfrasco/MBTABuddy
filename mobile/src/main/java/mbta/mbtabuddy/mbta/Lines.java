package mbta.mbtabuddy.mbta;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frascog on 2/17/16.
 */
public class Lines {

    private static Lines instance;
    public static final int Green = Color.rgb(0, 153, 51);
    public static final int Orange = Color.rgb(255,140,0);
    public static final int Purlpe = Color.rgb(121,46,102);
    //Lines
    public final Line RedLine;
    public final Line OrangeLine;
    public final Line GreenLineB;
    public final Line GreenLineC;
    public final Line GreenLineD;
    public final Line GreenLineE;
    public final Line BlueLine;

    public final Line CRFairmount;
    public final Line CRFitchburg;
    public final Line CRWorcester;
    public final Line CRFranklin;
    //All Lines
    public List<Line> lines;


    public Lines() {
        this.RedLine       = new Line("Red","Red Line",LineType.Subway,Color.RED);
        this.OrangeLine    = new Line("Orange","Orange Line",LineType.Subway,Orange);
        this.GreenLineB    = new Line("Green-B","Green Line B",LineType.Tram,Green);
        this.GreenLineC    = new Line("Green-C","Green Line C",LineType.Tram,Green);
        this.GreenLineD    = new Line("Green-D","Green Line D",LineType.Tram,Green);
        this.GreenLineE    = new Line("Green-E","Green Line E",LineType.Tram,Green);
        this.BlueLine      = new Line("Blue","Blue Line",LineType.Subway,Color.BLUE);

        this.CRFairmount   = new Line("CR-Fairmount","Fairmount Line",LineType.Commuter_Rail,Purlpe);
        this.CRFitchburg   = new Line("CR-Fitchburg","Fitchburg Line",LineType.Commuter_Rail,Purlpe);
        this.CRWorcester   = new Line("CR-Worcester","Framingham / Worcester Line",LineType.Commuter_Rail,Purlpe);
        this.CRFranklin   = new Line("CR-Franklin","Franklin Line",LineType.Commuter_Rail,Purlpe);
    }

    public static Lines getInstance(){
        if(instance == null){
            instance = new Lines();
        }
        return instance;
    }

    public List<Line> values() {
        if(lines == null) {
            lines = new ArrayList<Line>();
            lines.add(RedLine);
            lines.add(OrangeLine);
            lines.add(GreenLineB);
            lines.add(GreenLineC);
            lines.add(GreenLineD);
            lines.add(GreenLineE);
            lines.add(BlueLine);
            lines.add(CRFairmount);
            lines.add(CRFitchburg);
            lines.add(CRFranklin);
            lines.add(CRWorcester);
        }
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