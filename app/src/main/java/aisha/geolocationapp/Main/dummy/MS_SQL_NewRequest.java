package aisha.geolocationapp.Main.dummy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import aisha.geolocationapp.MS_SQL.Adapter;
import aisha.geolocationapp.MS_SQL.ConnectionClass;
import aisha.geolocationapp.MS_SQL.EmergencyModel;
import aisha.geolocationapp.R;
import aisha.geolocationapp.Services.DateandTime;
import aisha.geolocationapp.Services.LocationAddressService;

public class MS_SQL_NewRequest extends Fragment {


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

    DateandTime dateandtime = new DateandTime();



    final String emergencystatus = "1";
    final String datetime = dateandtime.getTodayDateTimeString();

    public MS_SQL_NewRequest() {
        // Required empty public constructor
    }

    public static aisha.geolocationapp.Main.NewrequestFragment newInstance() {
        aisha.geolocationapp.Main.NewrequestFragment fragment = new aisha.geolocationapp.Main.NewrequestFragment();
        return fragment;
    }


    public class AndroidSpinnerActivity extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.fragment_newrequest);
            final Spinner staticSpinner;
            //Inserting relevant information into the database at click of request button

            // Spinner element
            // Create an ArrayAdapter using the string array and a default spinner
            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                    .createFromResource(this, R.array.emergency_array,
                            android.R.layout.simple_spinner_item);

            staticSpinner = (Spinner) getActivity().findViewById(R.id.static_spinner);

            // Specify the layout to use when the list of choices appears
            staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            staticSpinner.setAdapter(staticAdapter);

            //Spinner OnselectedItem Listener
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

        }
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
                        aisha.geolocationapp.Main.dummy.MS_SQL_NewRequest.this.startActivity(intent);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newrequest, container, false);
        userid = (TextView) getActivity().findViewById(R.id.User_id);
        Userid = userid.getText().toString();
        Button _btnemergency = (Button) view.findViewById(R.id.btn_emergency);
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.static_spinner);


   return view;
    }

    // Async Task has three overrided methods,
    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(getActivity(), "Loading",
                    "Synchronising RecyclerView!", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null) {
                    success = false;
                } else {
                    // Change below query according to your own database.
                    String query = "INSERT into Emergency(COLUMN_USERID, COLUMN_EMERGENCYTYPE, COLUMN_EMERGENCYSTATUS, COLUMN_EMERGENCYLOCATION, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_DATETIME)" +
                            "VALUES(" + Userid + "," + emergencytype + "," + emergencystatus + "," + emergencylocation + "," + latitude + "," + longitude + "," + datetime + ")";

                    Statement stmt = null;
                    try {
                        stmt = conn.createStatement();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        try {
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
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