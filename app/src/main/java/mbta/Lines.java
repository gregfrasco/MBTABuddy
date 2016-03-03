package mbta;

import java.util.HashMap;

import mbta.mbtaAPI.MBTARoutes;

/**
 * Created by frascog on 2/17/16.
 */
public enum Lines {
    Red_Line,
    Orange_Line,
    Green_Line_B,
    Green_Line_C,
    Green_Line_D,
    Green_Line_E,
    Blue_Line,
    Mattapan_Line;

    public static Lines getLine(String lineID){
        return MBTARoutes.getInstance().getLine(lineID);
    }

    @Override public String toString(){
        switch(this){
            case Red_Line:
                return "Red Line";
            case Orange_Line:
                return "Orange Line";
            case Green_Line_B:
                return "Green Line: B";
            case Green_Line_C:
                return "Green Line: C";
            case Green_Line_D:
                return "Green Line: D";
            case Green_Line_E:
                return "Green Line: E";
            case Blue_Line:
                return "Blue Line";
            case Mattapan_Line:
                return  "Mattapan Line";
            default:
                return "";
        }
    }

    private static HashMap<Lines,Line> lines;

    public static Line getLine(Lines line){
        if(lines == null){
            initLines();
        }
        return lines.get(line);
    }

    private static void initLines() {
        lines = new HashMap<Lines, Line>();
        for(Lines line: Lines.values()){
            lines.put(line,new Line(line));
        }
    }
}
