package aisha.geolocationapp.MS_SQL;

/**
 * @author Joseph Udonsak
 */
public class Emergency {

    private String emergency_id;
    private String emergency_type;
    private String emergency_location;
    private String emergency_status;

    public Emergency() {
    }

    public Emergency(String id, String type, String location, String status) {
        this.emergency_id = id;
        this.emergency_type = type;
        this.emergency_location = location;
        this.emergency_status = status;
    }

    /**
     * @return the emergency_id
     */
    public String getEmergency_id() {
        return emergency_id;
    }

    /**
     * @param emergency_id the emergency_id to set
     */
    public void setEmergency_id(String emergency_id) {
        this.emergency_id = emergency_id;
    }

    /**
     * @return the emergency_type
     */
    public String getEmergency_type() {
        return emergency_type;
    }

    /**
     * @param emergency_type the emergency_type to set
     */
    public void setEmergency_type(String emergency_type) {
        this.emergency_type = emergency_type;
    }

    /**
     * @return the emergency_location
     */
    public String getEmergency_location() {
        return emergency_location;
    }

    /**
     * @param emergency_location the emergency_location to set
     */
    public void setEmergency_location(String emergency_location) {
        this.emergency_location = emergency_location;
    }

    /**
     * @return the emergency_status
     */
    public String getEmergency_status() {
        return emergency_status;
    }

    /**
     * @param emergency_status the emergency_status to set
     */
    public void setEmergency_status(String emergency_status) {
        this.emergency_status = emergency_status;
    }

    @Override
    public String toString() {
        return String.format("Emergency ID: %s\nEmergency Status: %s\nEmergency Location: %s\nEmergency Type: %s\n",
                this.emergency_id, this.emergency_status, this.emergency_location, this.emergency_type);
    }
}