package aisha.geolocationapp.Main;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentContainer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import aisha.geolocationapp.MS_SQL.EmergencyModel;
import aisha.geolocationapp.MS_SQL.Adapter;
import aisha.geolocationapp.MS_SQL.ConnectionClass;

import aisha.geolocationapp.R;
import aisha.geolocationapp.Services.DateandTime;
import aisha.geolocationapp.Services.LocationAddressService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class NewrequestFragment extends Fragment {

    String emergencytype;
    String longitude;
    String latitude;
    String emergencylocation;
    String Userid;
    TextView userid;


    private Adapter adapter;
    private ArrayList<EmergencyModel> emergencyModelArrayList;
    private boolean success = false;
    private ConnectionClass connectionClass = new ConnectionClass(); //Connection Class Variable

    private Context mContext = getActivity();

    private final int PERMISSION_REQUEST_CODE = 123;

    DateandTime dateandtime = new DateandTime();

    final String emergencystatus = "1";

    private String provider;
    Location loc;
    LocationManager locationManager;

    public NewrequestFragment() {
        // Required empty public constructor
    }

    public static NewrequestFragment newInstance() {
        NewrequestFragment fragment = new NewrequestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        NewrequestFragment.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            emergencylocation = locationAddress;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newrequest, container, false);
        userid = getActivity().findViewById(R.id.User_id);

        final Spinner staticSpinner;
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.emergency_array, android.R.layout.simple_spinner_item);
        staticSpinner = view.findViewById(R.id.static_spinner);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                Log.v("item", (String) parent.getItemAtPosition(position));
                emergencytype = staticSpinner.getItemAtPosition(staticSpinner.getSelectedItemPosition()).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        //The Button action to insert items in the database

        Button _btnemergency = (Button) view.findViewById(R.id.btn_emergency);
        _btnemergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SyncData Insertemergency = new SyncData();
                Insertemergency.execute("");
            }

        });

        return view;
    }
    // Async Task has three overrided methods,
    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "There is an Error. Please review ADM";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(getActivity(), "Registering Emergency",
                    "Connecting to Emergency server!", true);
        }

        @Override
        protected String doInBackground(String... strings)
        {
            try {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null) {
                    success = false;
                } else {

                    Userid = (String) userid.getText();
                    final String datetime = dateandtime.getTodayDateTimeString();


                    String query = "INSERT into Emergency(COLUMN_USERID, COLUMN_EMERGENCYTYPE, COLUMN_EMERGENCYSTATUS, COLUMN_EMERGENCYLOCATION, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_DATETIME)" +
                            " VALUES('" + Userid + "','" + emergencytype + "','" + emergencystatus + "','" + emergencylocation + "','" + latitude + "','" + longitude + "','" + datetime + "')";

                    Statement stmt = null;
                    try {
                        stmt = conn.createStatement();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    stmt.executeUpdate(query);
                          }
    } catch (Exception e)
    {
        e.printStackTrace();
        Writer writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        msg = writer.toString();
        success = false;
    }
            return msg;
        }
@Override
                protected void onPostExecute (String msg) // disimissing progress dialoge, showing error and setting up my listview
                {
                    progress.dismiss();
                    Toast.makeText(getActivity(), msg + "", Toast.LENGTH_LONG).show();
                    if (success == false) {
                    } else {
                        try {
                            final Spinner mSpinner = (Spinner) getActivity().findViewById(R.id.static_spinner);
                            String compareValue = "- Select an Emergency -";
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.emergency_array, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinner.setAdapter(adapter);
                            if (!compareValue.equals(null)) {
                                int spinnerPosition = adapter.getPosition(compareValue);
                                mSpinner.setSelection(spinnerPosition);
                            }
                            ;
                            Toast.makeText(getActivity(), "Stored Successfully!", Toast.LENGTH_SHORT).show();

                        } catch (Exception ex) {

                        }

                    }

                }

        }

}