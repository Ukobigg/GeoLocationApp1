package aisha.geolocationapp.Notification;

/**
 * Created by UkoDavid on 09/02/2018.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import aisha.geolocationapp.Notification.Constants;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        Log.d(TAG, "Refreshing GCM Registration Token");

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent); }
}
