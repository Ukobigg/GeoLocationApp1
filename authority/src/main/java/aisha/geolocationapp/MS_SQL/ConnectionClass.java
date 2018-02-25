package aisha.geolocationapp.MS_SQL;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by UkoDavid on 19/11/2017.
 */

public class ConnectionClass {
    String IP = "172.16.239.53:1433"; // if you have to add port then it would be like .i.e. 182.50.133.109:1433
    String DB = "EmergencyInfo"; //Name of Database
    String DBUserName = "username"; //Database user
    String DBPassword = "password"; //Database Password

    @SuppressLint("NewApi")
    public Connection CONN() throws SQLException, ClassNotFoundException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        String ConnectionString = "jdbc:jtds:sqlserver://uko.database.windows.net:1433/EmergencyInfo;user=username@uko;password=Udeme$1000;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        Log.i("Login", "Establishing Connection...");
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        DriverManager.setLoginTimeout(10);
            conn = DriverManager.getConnection(ConnectionString,DBUserName,DBPassword);

        return conn;
    }
}

