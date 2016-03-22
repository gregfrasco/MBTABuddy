
package mbta.mbtaAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AffectedServices {

    @SerializedName("services")
    @Expose
    private List<Service> services = new ArrayList<Service>();
    @SerializedName("elevators")
    @Expose
    private List<Object> elevators = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * 
     * @param services
     *     The services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     * 
     * @return
     *     The elevators
     */
    public List<Object> getElevators() {
        return elevators;
    }

    /**
     * 
     * @param elevators
     *     The elevators
     */
    public void setElevators(List<Object> elevators) {
        this.elevators = elevators;
    }

}
