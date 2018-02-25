package aisha.geolocationapp.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Selection;
import android.util.Log;

/**
 * Created by UkoDavid on 11/11/2017.
 */

public class EmergencyContentProvider extends ContentProvider {
    public static final String  LOG_TAG = EmergencyContentProvider.class.getSimpleName();
    private static final int EMERGENCY = 100;
    private static final int EMERGENCY_ID = 101;

    private SQLiteDBHelper sqLiteDBHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(EmergencyContract.CONTENT_AUTHORITY,EmergencyContract.PATH,EMERGENCY);
        sUriMatcher.addURI(EmergencyContract.CONTENT_AUTHORITY,EmergencyContract.PATH+"/#",EMERGENCY);
    }

    @Override
    public boolean onCreate(){
        sqLiteDBHelper=new SQLiteDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = sqLiteDBHelper.getReadableDatabase();
        Cursor cursor;
        int match=sUriMatcher.match(uri);
        switch (match){
            case EMERGENCY:
                cursor=database.query(EmergencyContract.EmergencyEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case EMERGENCY_ID:
                selection=EmergencyContract.EmergencyEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=database.query(EmergencyContract.EmergencyEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI"+uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case EMERGENCY:
                return EmergencyContract.EmergencyEntry.CONTENT_lIST_TYPE;
            case EMERGENCY_ID:
                return EmergencyContract.EmergencyEntry.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalArgumentException("Unkown URI "+uri+" with match "+match);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match=sUriMatcher.match(uri);
        switch (match){
            case EMERGENCY:
                return insertEmergency(uri,values);
                default: throw new IllegalArgumentException("Insertion is not supported for"+uri);}
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

    final int match=sUriMatcher.match(uri);
        selection=EmergencyContract.EmergencyEntry._ID+"=?"+uri.getLastPathSegment();
        selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
    switch (match){
        case EMERGENCY:

            return updateEmergency(uri,values,selection,selectionArgs);
        case EMERGENCY_ID:

            return updateEmergency(uri,values,selection,selectionArgs);

        default:
            throw new IllegalArgumentException("Cannot query unknown URI"+uri);}

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
            SQLiteDatabase database = sqLiteDBHelper.getWritableDatabase();
            int rowsDeleted;
        final int match=sUriMatcher.match(uri);
        switch (match){
            case EMERGENCY:
                rowsDeleted=database.delete(EmergencyContract.EmergencyEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case EMERGENCY_ID:
                selection=EmergencyContract.EmergencyEntry._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted=database.delete(EmergencyContract.EmergencyEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for"+uri);}
    if (rowsDeleted !=0){
            getContext().getContentResolver().notifyChange(uri,null);
    }
    return rowsDeleted;
    }




    private int updateEmergency(Uri uri, ContentValues values, String selection, String selectionArgs[]) {
        if (values.containsKey(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYTYPE)){
            String EmergencyType = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYTYPE);

            if (EmergencyType == null) {
                throw new IllegalArgumentException("Emergency Type Required");
            }
        }

        if (values.containsKey(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYSTATUS)){
            String EmergencyStatus = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYSTATUS);
            if (EmergencyStatus == null) {
                throw new IllegalArgumentException("Emergency Status Required");}
        }

        if (values.containsKey(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYLOCATION)){
            String EmergencyLocation = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYTYPE);

            if ( EmergencyLocation== null) {
                throw new IllegalArgumentException("Emergency Type Required");
            }
        }

        if (values.containsKey(EmergencyContract.EmergencyEntry.COLUMN_DATETIME)){
            String Datetime = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_DATETIME);
            if (Datetime == null) {
                throw new IllegalArgumentException("Date and Time Required");}
        }

        if (values.containsKey(EmergencyContract.EmergencyEntry.COLUMN_LATITUDE)){
            String Emergencylatitude = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_LATITUDE);
            if (Emergencylatitude == null) {
                throw new IllegalArgumentException("Latitude Required");}
        }

        if (values.containsKey(EmergencyContract.EmergencyEntry.COLUMN_LONGITUDE)){
            String EmergencyLongitude = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_LONGITUDE);
            if (EmergencyLongitude == null) {
                throw new IllegalArgumentException("Longitude Required");}
        }

        if (values.containsKey(EmergencyContract.EmergencyEntry.COLUMN_USERID)) {
            String UserID = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_USERID);
            if (UserID == null) {
                throw new IllegalArgumentException("UserID Required");
            }
        }
        if (values.size()==0){
            return 0;}

            SQLiteDatabase database = sqLiteDBHelper.getWritableDatabase();
        int rowsUpdated=database.update(EmergencyContract.EmergencyEntry.TABLE_NAME,values,selection,selectionArgs);

        if (rowsUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;

    }
    private Uri insertEmergency(Uri uri, ContentValues values) {
        String EmergencyType = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYTYPE);

        if (EmergencyType == null) {
            throw new IllegalArgumentException("Emergency Type Required");
        }
        String EmergencyStatus = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYSTATUS);
        if (EmergencyStatus == null) {
            throw new IllegalArgumentException("Emergency Status Required");}

        String EmergencyLocation = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_EMERGENCYLOCATION);
        if (EmergencyLocation == null) {
            throw new IllegalArgumentException("Emergency Location Required");}

        String Datetime = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_DATETIME);
        if (Datetime == null) {
            throw new IllegalArgumentException("Date and Time Required");}

        String Emergencylatitude = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_LATITUDE);
        if (Emergencylatitude == null) {
            throw new IllegalArgumentException("Latitude Required");}

        String EmergencyLongitude = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_LONGITUDE);
        if (EmergencyLongitude == null) {
            throw new IllegalArgumentException("Longitude Required");}

        String UserID = values.getAsString(EmergencyContract.EmergencyEntry.COLUMN_USERID);
        if (UserID == null) {
            throw new IllegalArgumentException("UserID Required");}

        SQLiteDatabase database = sqLiteDBHelper.getWritableDatabase();
        long id = database.insert(EmergencyContract.EmergencyEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }
getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

}
