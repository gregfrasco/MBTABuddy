package mbta;

/**
 * Created by frascog on 2/5/16.
 */
public class MBTARoutes {

    public static Routes Red_Line = Routes.Red_Line;
    public static Routes Orange_Line = Routes.Orange_Line;
    public static Routes Green_Line_B = Routes.Green_Line_B;
    public static Routes Green_Line_C = Routes.Green_Line_C;
    public static Routes Green_Line_D = Routes.Green_Line_D;
    public static Routes Green_Line_E = Routes.Green_Line_E;
    public static Routes Blue_Line = Routes.Blue_Line;

    public enum Routes {
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


}
