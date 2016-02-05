package mbta;

/**
 * Created by frascog on 2/5/16.
 */
public enum MBTARoutes {
    Red_Line,
    Orange_Line,
    Green_Line_B,
    Green_Line_C,
    Green_Line_D,
    Green_Line_E,
    Blue_Line;
    
    @Override
    public String toString() {
        return super.toString().replaceAll("_"," ");
    }
}
