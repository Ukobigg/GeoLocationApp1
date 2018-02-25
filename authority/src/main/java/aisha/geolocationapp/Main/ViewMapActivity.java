package aisha.geolocationapp.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

import aisha.geolocationapp.BuildConfig;
import aisha.geolocationapp.R;

/**
 * An activity that displays a map showing the place at the device's current location.
 */
        public class ViewMapActivity extends AppCompatActivity implements
                OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private LatLng EMERGENCYLOCATION;
    private LatLng MYLOCATION;
    private GoogleMap mMap;


    private static final String TAG = ViewMapActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private FusedLocationProviderClient mFusedLocationClient;

    protected Location mLastLocation;

    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private TextView txtlatlng_saved;
    private TextView txtlatlng_current;

    String latitude_current = "Latitude";
    String longitude_current = "Longitude";
    String latitude_saved;
    String longitude_saved;
    String emergencyid;
    String emergencytype;
    String userid;
    TextView txtsecurity_message;
    TextView txtEmergency_status;
    TextView txtEmergencyid;
    TextView txtEmergency_type;
    TextView txtuserid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get the map and register for the ready callback
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        new OnMapAndViewReadyListener(mapFragment, this);

        Intent intent = getIntent();

        latitude_saved = intent.getStringExtra("EXTRA_LATITUDE");
        longitude_saved = intent.getStringExtra("EXTRA_LONGITUDE");
        emergencyid = intent.getStringExtra("EXTRA_EMERGENCYID");
        emergencytype = intent.getStringExtra("EXTRA_EMERGENCY_TYPE");
        userid = intent.getStringExtra("EXTRA_USERID");


        txtlatlng_saved = findViewById(R.id.LatLng_SavedMapTag);
        txtlatlng_current = findViewById(R.id.LatLng_CurrentMapTag);
        txtEmergencyid = findViewById(R.id.EmergencyIdMapTag);
        txtEmergency_type = findViewById(R.id.EmergencyTypeMapTag);
        txtuserid = findViewById(R.id.UserIDMapTag);
        txtEmergency_status = findViewById(R.id.EmergencystatusMapTag);
        txtsecurity_message = findViewById(R.id.SecurityMessage);
        EMERGENCYLOCATION = new LatLng(Double.parseDouble(latitude_saved), Double.parseDouble(longitude_saved));


        txtEmergencyid.setText(emergencyid);
        txtEmergency_type.setText(emergencytype);
        txtuserid.setText(userid);
        txtlatlng_saved.setText("Latitude: " + latitude_saved + ", Longitude: " + longitude_saved);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            txtlatlng_current.setText(String.format(Locale.ENGLISH, "%f: %f",
                                    mLastLocation.getLatitude(),
                                    mLastLocation.getLongitude()));
                            MYLOCATION = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }


    private void showSnackbar(final String text) {
        View container = findViewById(R.id.map_container2);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(ViewMapActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    /**
     * Move the camera to center on Darwin.
     */
    public void showMYLOCATION(View v) {
        // Wait until map is ready
        if (mMap == null) {
            return;
        }

        // Center camera on Adelaide marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MYLOCATION, 10f));
    }

    /**
     * Move the camera to center on Adelaide.
     */
    public void showEMERGENCYLOCATION(View v) {
        // Wait until map is ready
        if (mMap == null) {
            return;
        }


        // Center camera on Adelaide marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EMERGENCYLOCATION, 10f));
    }

    // Create bounds that include all locations of the map
    public void showNigeria(View v) {
        // Wait until map is ready
        if (mMap == null) {
            return;
        }
    LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
            .include(MYLOCATION)
            .include(EMERGENCYLOCATION);

    // Move camera to show all markers and locations
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),20));

}
    /**
     * Called when the map is ready to add all markers and objects to the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();
        showNigeria(null);
    }

    /**
     * Add Markers with default info windows to the map.
     */
    private void addMarkers() {
        mMap.addMarker(new MarkerOptions()
                .position(EMERGENCYLOCATION)
                .title("Emergency Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mMap.addMarker(new MarkerOptions()
                .position(MYLOCATION)
                .title("Your Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

    }
}




