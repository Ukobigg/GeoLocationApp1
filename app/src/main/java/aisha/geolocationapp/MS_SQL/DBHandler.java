package aisha.geolocationapp.MS_SQL;

/**
 * Created by UkoDavid on 03/12/2017.
 */

import com.mysql.jdbc.CommunicationsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joseph Udonsak
 */
public class DBHandler {

    private final String host;
    private final String uName;
    private final String password;
    private Connection conn;
    private Statement stmnt;

    public DBHandler() {
        this.stmnt = null;
        this.conn = null;
        this.uName = "root";
        this.host = "jdbc:mysql://localhost:3306/employeeinfo";//link to localhost db
        this.password = "password"; //password
        setupConnection();
    }

    private void setupConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, uName, password);
            stmnt = conn.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet executeQuery(String SQLCommand) {
        try {
            return stmnt.executeQuery(SQLCommand);
        } catch (CommunicationsException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            resetConnection();
            try {
                return stmnt.executeQuery(SQLCommand);
            } catch (SQLException ex1) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            resetConnection();
            try {
                stmnt.executeQuery(SQLCommand);
            } catch (SQLException ex1) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public ResultSet searchQuery(String SQLCommand) {
        try {
            return stmnt.executeQuery(SQLCommand);
        } catch (CommunicationsException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            resetConnection();
            try {
                return stmnt.executeQuery(SQLCommand);
            } catch (SQLException ex1) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            resetConnection();
            try {
                stmnt.executeQuery(SQLCommand);
            } catch (SQLException ex1) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

    public void updateDatabase(String SQLCommand) {
        try {
            stmnt.executeUpdate(SQLCommand);
            //System.out.println("Database updated successfully");
        } catch (CommunicationsException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            resetConnection();
            try {
                stmnt.executeUpdate(SQLCommand);
            } catch (SQLException ex1) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            resetConnection();
            try {
                stmnt.executeUpdate(SQLCommand);
            } catch (SQLException ex1) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private void resetConnection() {
        closeConnection();
        setupConnection();
    }

    private void closeConnection() {
        try {
            stmnt.close();
            conn.close();
        } catch (SQLException ex) {
        }
    }
}
