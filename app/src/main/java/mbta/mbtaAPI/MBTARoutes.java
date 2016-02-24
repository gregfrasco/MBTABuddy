package mbta.mbtaAPI;

import java.util.HashMap;

import mbta.Line;
import mbta.Lines;

/**
 * Created by frascog on 2/5/16.
 */
public class MBTARoutes {

    private HashMap<Lines,String> compatiableLines;
    private HashMap<String,Lines> getLines;
    private static MBTARoutes instance;

    public static MBTARoutes getInstance() {
        if(instance == null){
            instance = new MBTARoutes();
        }
        return instance;
    }

    private MBTARoutes() {
        this.compatiableLines = new HashMap<Lines,String>();
        this.getLines = new HashMap<String,Lines>();
        this.put(Lines.Red_Line,"Red");
        this.put(Lines.Orange_Line,"Orange");
        this.put(Lines.Green_Line_B,"Green-B");
        this.put(Lines.Green_Line_C,"Green-C");
        this.put(Lines.Green_Line_D,"Green-D");
        this.put(Lines.Green_Line_E,"Green-E");
        this.put(Lines.Blue_Line,"Blue");
        this.put(Lines.Mattapan_Line,"Mattapan");
    }

    public String getLineID(Lines lines){
        return this.compatiableLines.get(lines);
    }
    public Lines getLine(String id){
        return this.getLines.get(id);
    }

    private void put(Lines lines,String id){
        this.compatiableLines.put(lines, id);
        this.getLines.put(id,lines);
    }
}
