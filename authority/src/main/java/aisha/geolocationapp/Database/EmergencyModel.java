package aisha.geolocationapp.Database;

import java.io.Serializable;

/**
 * Created by UkoDavid on 08/11/2017.
 */

public class EmergencyModel implements Serializable{
    private String userid, emergencytype, emergencystatus, emergencylocation, datetime, longitude, latitude;
    private int emergencyid;

    public String getUserid(){return userid;}
    public void setUserid(String userid){this.userid=userid;}

    public int getEmergencyid(){return emergencyid;}
    public void setEmergencyid(int emergencyid){this.emergencyid=emergencyid;}

    public String getEmergencytype(){return emergencytype;}
    public void setEmergencytype(String emergencytype){this.emergencytype=emergencytype;}

    public String getEmergencystatus(){return emergencystatus;}
    public void setEmergencystatus(String emergencystatus){this.emergencystatus=emergencystatus;}

    public String getEmergencylocation(){return emergencylocation;}
    public void setEmergencylocation(String emergencylocation){this.emergencylocation=emergencylocation;}

    public String getDatetime(){return datetime;}
    public void setDatetime(String datetime){this.datetime=datetime;}

    public String getLatitude(){return latitude;}
    public void setLatitude(String latitude){this.latitude=latitude;}

    public String getLongitude(){return longitude;}
    public void setLongitude(String longitude){this.longitude=longitude;}
}
