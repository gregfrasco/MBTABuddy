package mbta;

/**
 * Created by Greg on 2016-02-04.
 */
public class MBTA implements Runnable{

    private final String MBTAAPI = "http://realtime.mbta.com/developer/api/v2/";
    private final String APIKey =
    private static MBTA instance;

    public static MBTA getInstance(){
        if(instance == null){
           instance = new MBTA();
        }
        return instance;
    }

    private MBTA(){

    }

    @Override
    public void run() {

    }
}
