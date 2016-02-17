package mbta.mbtaAPI;

import java.util.HashMap;

import mbta.Lines;

/**
 * Created by frascog on 2/5/16.
 */
public class MBTARoutes {

    private HashMap<Lines,String> compatiableLines;
    private static MBTARoutes instance;

    public static MBTARoutes getInstance() {
        if(instance == null){
            instance = new MBTARoutes();
        }
        return instance;
    }

    private MBTARoutes() {
        this.compatiableLines = new HashMap<Lines,String>();
        this.compatiableLines.put(Lines.Red_Line,"Red");
        this.compatiableLines.put(Lines.Orange_Line,"Orange");
        this.compatiableLines.put(Lines.Green_Line_B,"Green-B");
        this.compatiableLines.put(Lines.Green_Line_C,"Green-C");
        this.compatiableLines.put(Lines.Green_Line_D,"Green-D");
        this.compatiableLines.put(Lines.Green_Line_E,"Green-E");
        this.compatiableLines.put(Lines.Blue_Line,"Blue");
        this.compatiableLines.put(Lines.Mattapan_Line,"Mattapan");
    }

    public String getLineID(Lines lines){
        return this.compatiableLines.get(lines);
    }
}
