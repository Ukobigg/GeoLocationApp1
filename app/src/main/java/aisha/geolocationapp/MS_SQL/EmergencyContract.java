package aisha.geolocationapp.MS_SQL;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by UkoDavid on 12/11/2017.
 */

public final class EmergencyContract {
    private EmergencyContract(){}

    public static final String CONTENT_AUTHORITY = "aisha.geolocationapp";
    public static final String PATH  = "emergency-path";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String DATABASE_NAME = "dictionary";
    public static final int DATABASE_VERSION = 1;

    public static class EmergencyEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH);

        public static final String CONTENT_lIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH;

        public static final String TABLE_NAME = "emergency";
        public static final String _ID =  BaseColumns._ID;
        public static final String COLUMN_EMERGENCYLOCATION =  "emergencylocation";
        public static final String COLUMN_EMERGENCYTYPE =  "emergencytype";
        public static final String COLUMN_EMERGENCYSTATUS =  "emergencystatus";
        public static final String COLUMN_LONGITUDE =  "longitude";
        public static final String COLUMN_LATITUDE =  "latitude";
        public static final String COLUMN_DATETIME =  "datetime";
        public static final String COLUMN_USERID =  "userid";
    }
}

