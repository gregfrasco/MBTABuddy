package mbta;

/**
 * Created by Greg on 2016-02-04.
 */
public class MBTA {

    private static MBTA instance;

    public static MBTA getInstance(){
        if(instance == null){
           instance = new MBTA();
        }
        return instance;
    }

    private MBTA(){

    }
}
