package aisha.geolocationapp.Database;

/**
 * Created by UkoDavid on 04/11/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by UkoDavid on 02/10/2017.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "info.db";
    private static final int DATABASE_VERSION = 1;
    //Declaring Table columns for Table to store User Login Information
    public static final String USERTABLE_NAME = "profile";
    public static final String COLUMN_ID =  "userid";
    public static final String COLUMN_FULLNAME =  "fullname";
    public static final String COLUMN_EMAIL =  "email";
    public static final String COLUMN_PASSWORD =  "password";

    //Declaring Table columns for Table to store Emergency Event
    public static final String EMERGENCYTABLE_NAME = "emergency";
    public static final String COLUMN_EMERGENCYID =  "emergencyid";
    public static final String COLUMN_EMERGENCYLOCATION =  "emergencylocation";
    public static final String COLUMN_EMERGENCYTYPE =  "emergencytype";
    public static final String COLUMN_EMERGENCYSTATUS =  "emergencystatus";
    public static final String COLUMN_LONGITUDE =  "longitude";
    public static final String COLUMN_LATITUDE =  "latitude";
    public static final String COLUMN_DATETIME =  "datetime";
    public static final String COLUMN_USERID =  "userid";

    //SQL TO Create Table for Userdata
    private static final String CREATE_USERTABLE_QUERY =
            "CREATE TABLE " + USERTABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FULLNAME + " TEXT, "+
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +")";

    //SQL TO Create Table for Emergency
    private static final String CREATE_EMERGENCYTABLE_QUERY =
            "CREATE TABLE " + EMERGENCYTABLE_NAME + " (" +
                    COLUMN_EMERGENCYID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COLUMN_USERID + "TEXT"+
                    COLUMN_EMERGENCYLOCATION + " TEXT, "+
                    COLUMN_EMERGENCYTYPE + " TEXT, " +
                    COLUMN_EMERGENCYSTATUS + " TEXT, " +
                    COLUMN_LONGITUDE + " TEXT, " +
                    COLUMN_LATITUDE + " TEXT, " +
                    COLUMN_DATETIME + " TEXT " + ")";
    //modified constructor
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("USER TABLE",CREATE_USERTABLE_QUERY);
        Log.d("EMERGENCY TABLE", CREATE_EMERGENCYTABLE_QUERY);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERTABLE_QUERY);
        sqLiteDatabase.execSQL(CREATE_EMERGENCYTABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USERTABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EMERGENCYTABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addEmergency (String userid, String emergencytype, String emergencystatus, String emergencylocation, String datetime, String longitude, String latitude ) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        ContentValues valuesEmergency = new ContentValues();

       valuesEmergency.put(COLUMN_USERID,userid);
        valuesEmergency.put(COLUMN_EMERGENCYTYPE,emergencytype);
        valuesEmergency.put(COLUMN_EMERGENCYSTATUS,emergencystatus);
        valuesEmergency.put(COLUMN_EMERGENCYLOCATION,emergencylocation);
        valuesEmergency.put(COLUMN_LATITUDE,latitude);
        valuesEmergency.put(COLUMN_LONGITUDE,longitude);
        valuesEmergency.put(COLUMN_DATETIME,datetime);
        long id = sqLiteDatabase.insert(EMERGENCYTABLE_NAME,null,valuesEmergency);
sqLiteDatabase.setTransactionSuccessful();

    sqLiteDatabase.endTransaction();
    sqLiteDatabase.close();

}
// All Requested Emergencies before Approval
    public ArrayList<EmergencyModel> getAllEmergencies() {
        ArrayList<EmergencyModel> EmergencyModelArrayList = new ArrayList<EmergencyModel>();
        EmergencyModel emergencyModel = new EmergencyModel();

        String selectQuery = "SELECT  * FROM " + EMERGENCYTABLE_NAME+" WHERE "+COLUMN_EMERGENCYID+" = "+emergencyModel.getEmergencyid()+" AND "+ COLUMN_EMERGENCYSTATUS+" = 1";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                emergencyModel.setEmergencyid(c.getInt(c.getColumnIndex(COLUMN_EMERGENCYID)));
                emergencyModel.setUserid(c.getString(c.getColumnIndex(COLUMN_USERID)));
                emergencyModel.setDatetime(c.getString(c.getColumnIndex(COLUMN_DATETIME)));
                emergencyModel.setEmergencytype(c.getString(c.getColumnIndex(COLUMN_EMERGENCYTYPE)));
                emergencyModel.setEmergencylocation(c.getString(c.getColumnIndex(COLUMN_EMERGENCYLOCATION)));
                emergencyModel.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                emergencyModel.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                emergencyModel.setEmergencystatus(c.getString(c.getColumnIndex(COLUMN_EMERGENCYSTATUS)));


                Log.d("oppp",selectQuery);
                // adding to Emergencies list
                EmergencyModelArrayList.add(emergencyModel);
            } while (c.moveToNext());
        }
        return EmergencyModelArrayList;
    }

    // All Requested Emergencies Approved
    public ArrayList<EmergencyModel> getAllEmergenciesApproved() {
        ArrayList<EmergencyModel> EmergencyModelArrayList = new ArrayList<EmergencyModel>();
        EmergencyModel emergencyModel = new EmergencyModel();

        String selectQuery = "SELECT  * FROM " + EMERGENCYTABLE_NAME+" WHERE "+COLUMN_EMERGENCYID+" = "+emergencyModel.getEmergencyid()+" AND "+ COLUMN_EMERGENCYSTATUS+" = 2";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                emergencyModel.setEmergencyid(c.getInt(c.getColumnIndex(COLUMN_EMERGENCYID)));
                emergencyModel.setUserid(c.getString(c.getColumnIndex(COLUMN_USERID)));
                emergencyModel.setDatetime(c.getString(c.getColumnIndex(COLUMN_DATETIME)));
                emergencyModel.setEmergencytype(c.getString(c.getColumnIndex(COLUMN_EMERGENCYTYPE)));
                emergencyModel.setEmergencylocation(c.getString(c.getColumnIndex(COLUMN_EMERGENCYLOCATION)));
                emergencyModel.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                emergencyModel.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                emergencyModel.setEmergencystatus(c.getString(c.getColumnIndex(COLUMN_EMERGENCYSTATUS)));


                Log.d("oppp",selectQuery);
                // adding to Emergencies list
                EmergencyModelArrayList.add(emergencyModel);
            } while (c.moveToNext());
        }
        return EmergencyModelArrayList;
    }

    // All Archived Emergencies
    public ArrayList<EmergencyModel> getAllEmergenciesArchived() {
        ArrayList<EmergencyModel> EmergencyModelArrayList = new ArrayList<EmergencyModel>();
        EmergencyModel emergencyModel = new EmergencyModel();

        String selectQuery = "SELECT  * FROM " + EMERGENCYTABLE_NAME+" WHERE "+COLUMN_EMERGENCYID+" = "+emergencyModel.getEmergencyid()+" AND "+ COLUMN_EMERGENCYSTATUS+" = 3";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                emergencyModel.setEmergencyid(c.getInt(c.getColumnIndex(COLUMN_EMERGENCYID)));
                emergencyModel.setUserid(c.getString(c.getColumnIndex(COLUMN_USERID)));
                emergencyModel.setDatetime(c.getString(c.getColumnIndex(COLUMN_DATETIME)));
                emergencyModel.setEmergencytype(c.getString(c.getColumnIndex(COLUMN_EMERGENCYTYPE)));
                emergencyModel.setEmergencylocation(c.getString(c.getColumnIndex(COLUMN_EMERGENCYLOCATION)));
                emergencyModel.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                emergencyModel.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                emergencyModel.setEmergencystatus(c.getString(c.getColumnIndex(COLUMN_EMERGENCYSTATUS)));


                Log.d("oppp",selectQuery);
                // adding to Emergencies list
                EmergencyModelArrayList.add(emergencyModel);
            } while (c.moveToNext());
        }
        return EmergencyModelArrayList;
    }

    // All Deleted Emergencies
    public ArrayList<EmergencyModel> getAllEmergenciesDeleted() {
        ArrayList<EmergencyModel> EmergencyModelArrayList = new ArrayList<EmergencyModel>();
        EmergencyModel emergencyModel = new EmergencyModel();

        String selectQuery = "SELECT  * FROM " + EMERGENCYTABLE_NAME+" WHERE "+COLUMN_EMERGENCYID+" = "+emergencyModel.getEmergencyid()+" AND "+ COLUMN_EMERGENCYSTATUS+" = 4";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                emergencyModel.setEmergencyid(c.getInt(c.getColumnIndex(COLUMN_EMERGENCYID)));
                emergencyModel.setUserid(c.getString(c.getColumnIndex(COLUMN_USERID)));
                emergencyModel.setDatetime(c.getString(c.getColumnIndex(COLUMN_DATETIME)));
                emergencyModel.setEmergencytype(c.getString(c.getColumnIndex(COLUMN_EMERGENCYTYPE)));
                emergencyModel.setEmergencylocation(c.getString(c.getColumnIndex(COLUMN_EMERGENCYLOCATION)));
                emergencyModel.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                emergencyModel.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                emergencyModel.setEmergencystatus(c.getString(c.getColumnIndex(COLUMN_EMERGENCYSTATUS)));


                Log.d("oppp",selectQuery);
                // adding to Emergencies list
                EmergencyModelArrayList.add(emergencyModel);
            } while (c.moveToNext());
        }
        return EmergencyModelArrayList;
    }






    public void DeleteEmergency(int emergencyid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Approving emergency in emergency table
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMERGENCYSTATUS, "4");
        db.update(EMERGENCYTABLE_NAME, values, COLUMN_EMERGENCYSTATUS + " = ?", new String[]{String.valueOf(emergencyid)});
    }

    public void ApproveEmergency(int emergencyid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Approving emergency in emergency table
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMERGENCYSTATUS, "2");
        db.update(EMERGENCYTABLE_NAME, values, COLUMN_EMERGENCYSTATUS + " = ?", new String[]{String.valueOf(emergencyid)});
    }

    public void ArchiveEmergency(int emergencyid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Approving emergency in emergency table
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMERGENCYSTATUS, "3");
        db.update(EMERGENCYTABLE_NAME, values, COLUMN_EMERGENCYSTATUS + " = ?", new String[]{String.valueOf(emergencyid)});
    }
}

