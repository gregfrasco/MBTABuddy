
package mbta.mbtaAPI;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Vehicle {

    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("vehicle_lat")
    @Expose
    private String vehicleLat;
    @SerializedName("vehicle_lon")
    @Expose
    private String vehicleLon;
    @SerializedName("vehicle_bearing")
    @Expose
    private String vehicleBearing;
    @SerializedName("vehicle_speed")
    @Expose
    private String vehicleSpeed;
    @SerializedName("vehicle_timestamp")
    @Expose
    private String vehicleTimestamp;

    /**
     * 
     * @return
     *     The vehicleId
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * 
     * @param vehicleId
     *     The vehicle_id
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * 
     * @return
     *     The vehicleLat
     */
    public String getVehicleLat() {
        return vehicleLat;
    }

    /**
     * 
     * @param vehicleLat
     *     The vehicle_lat
     */
    public void setVehicleLat(String vehicleLat) {
        this.vehicleLat = vehicleLat;
    }

    /**
     * 
     * @return
     *     The vehicleLon
     */
    public String getVehicleLon() {
        return vehicleLon;
    }

    /**
     * 
     * @param vehicleLon
     *     The vehicle_lon
     */
    public void setVehicleLon(String vehicleLon) {
        this.vehicleLon = vehicleLon;
    }

    /**
     * 
     * @return
     *     The vehicleBearing
     */
    public String getVehicleBearing() {
        return vehicleBearing;
    }

    /**
     * 
     * @param vehicleBearing
     *     The vehicle_bearing
     */
    public void setVehicleBearing(String vehicleBearing) {
        this.vehicleBearing = vehicleBearing;
    }

    /**
     * 
     * @return
     *     The vehicleSpeed
     */
    public String getVehicleSpeed() {
        return vehicleSpeed;
    }

    /**
     * 
     * @param vehicleSpeed
     *     The vehicle_speed
     */
    public void setVehicleSpeed(String vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    /**
     * 
     * @return
     *     The vehicleTimestamp
     */
    public String getVehicleTimestamp() {
        return vehicleTimestamp;
    }

    /**
     * 
     * @param vehicleTimestamp
     *     The vehicle_timestamp
     */
    public void setVehicleTimestamp(String vehicleTimestamp) {
        this.vehicleTimestamp = vehicleTimestamp;
    }

}
