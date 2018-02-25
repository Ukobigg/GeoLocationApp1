package aisha.geolocationapp.MS_SQL;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sourceforge.jtds.jdbc.*;


/**
 * Created by UkoDavid on 19/11/2017.
 */

public class ConnectionClass {
    String IP = "uko.database.windows.net"; // if you have to add port then it would be like .i.e. 182.50.133.109:1433
    String DB = "EmergencyInfo"; //Name of Database
    String DBUserName = "username"; //Database user
    String DBPassword = "Udeme$1000"; //Database Password

    @SuppressLint("NewApi")
    public Connection CONN()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        String ConnectionString = "jdbc:jtds:sqlserver://uko.database.windows.net:1433/EmergencyInfo;user=username@uko;password=Udeme$1000;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        try {
            Log.i("Login", "Establishing Connection...");
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(ConnectionString,DBUserName,DBPassword);
        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return conn;
    }
}

