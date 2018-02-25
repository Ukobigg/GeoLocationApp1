package aisha.geolocationapp.MS_SQL;

import java.io.Serializable;

/**
 * Created by UkoDavid on 08/11/2017.
 */

public class EmergencyModel{
    public String userid, emergencytype, emergencystatus, emergencylocation, datetime, longitude, latitude;
    private int emergencyid;

    public EmergencyModel(String userid, int emergencyid, String emergencytype, String emergencystatus, String emergencylocation, String latitude, String longitude, String datetime)
    {this.userid=userid;
    this.emergencyid=emergencyid;
        this.emergencytype=emergencytype;
        this.emergencystatus=emergencystatus;
        this.emergencylocation=emergencylocation;
        this.datetime=datetime;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getUserid(){return this.userid;}

    public int getEmergencyid(){return this.emergencyid;}

    public String getEmergencytype(){return this.emergencytype;}

    public String getEmergencystatus(){return this.emergencystatus;}

    public String getEmergencylocation(){return this.emergencylocation;}

    public String getDatetime(){return this.datetime;}

    public String getLatitude(){return this.latitude;}

    public String getLongitude(){return this.longitude;}
}
