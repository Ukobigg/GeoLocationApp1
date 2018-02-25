package aisha.geolocationapp.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import aisha.geolocationapp.MS_SQL.EmergencyModel;
import aisha.geolocationapp.MS_SQL.Adapter;
import aisha.geolocationapp.MS_SQL.ConnectionClass;
//EmergencyModel redirect
import aisha.geolocationapp.MS_SQL.RecyclerItemClickListener;
import aisha.geolocationapp.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<EmergencyModel> emergencyModelArrayList;
    private ConnectionClass connectionClass;
    private boolean success = false; // boolean

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_EMERGENCYID = "EXTRA_QUOTE";
    private static final String EXTRA_EMERGENCYTYPE = "EXTRA_ATTR";
    private static final String EXTRA_EMERGENCYSTATUS = "EXTRA_ATTR";
    private static final String EXTRA_LONGITUDE = "EXTRA_ATTR";
    private static final String EXTRA_LATITUDE = "EXTRA_ATTR";
    private static final String EXTRA_DATETIME = "EXTRA_ATTR";
    private static final String EXTRA_USERID = "EXTRA_ATTR";
    private static final String EXTRA_EMERGENCYLOCATION = "EXTRA_ATTR";


    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        emergencyModelArrayList = new ArrayList<EmergencyModel>();
        recyclerView = view.findViewById(R.id.RecyclerviewNotification);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        String latitude_recycler = emergencyModelArrayList.get(itemPosition).getLatitude();
                        String longitude_recycler = emergencyModelArrayList.get(itemPosition).getLongitude();
                        String EmergencyID_recycler = String.valueOf(emergencyModelArrayList.get(itemPosition).getEmergencyid());
                        String EmergencyType_recycler = emergencyModelArrayList.get(itemPosition).getEmergencytype();
                        String EmergencyStatus_recycler = emergencyModelArrayList.get(itemPosition).getEmergencystatus();
                        String UserID_recycler = emergencyModelArrayList.get(itemPosition).getUserid();

                        Intent intent = new Intent(getActivity().getBaseContext(), ViewMapActivity.class);
                        intent.putExtra("EXTRA_LATITUDE", latitude_recycler);
                        intent.putExtra("EXTRA_LONGITUDE", longitude_recycler);
                        intent.putExtra("EXTRA_EMERGENCYID", EmergencyID_recycler);
                        intent.putExtra("EXTRA_EMERGENCY_TYPE", EmergencyType_recycler);
                        intent.putExtra("EXTRA_USERID", UserID_recycler);
                        intent.putExtra("EXTRA_EMERGENCY_STATUS", EmergencyStatus_recycler);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        connectionClass = new ConnectionClass(); // Connection Class Initialization

        SyncData Approvedemergency = new SyncData(); // Calling Async Task
        Approvedemergency.execute("");
        Log.d(TAG, "onCreateView");
        return view;
    }

    // Async Task has three overrided methods,
    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(getActivity(), "Synchronising",
                    "RecyclerView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try
            {
                Connection conn = connectionClass.CONN(); //Connection Object
                if (conn == null)
                {
                    success = false;
                }
                else {
                    // Change below query according to your own database.
                    String query = "SELECT * FROM Emergency WHERE COLUMN_EMERGENCYSTATUS = 2 ORDER BY COLUMN_DATETIME ASC";
                           // "SELECT * FROM dbo.Emergency WHERE COLUMN_EMERGENCYSTATUS = 2";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                emergencyModelArrayList.add(new EmergencyModel(rs.getString("COLUMN_USERID"),rs.getInt("_ID"),rs.getString("COLUMN_EMERGENCYTYPE"),rs.getString("COLUMN_EMERGENCYSTATUS"), rs.getString("COLUMN_EMERGENCYLOCATION"),rs.getString("COLUMN_LATITUDE"),rs.getString("COLUMN_LONGITUDE"),rs.getString("COLUMN_DATETIME")));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
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
        protected void onPostExecute(String msg) // dismissing progress dialogue, showing error and setting up my listview
        {
            Adapter adapter = new Adapter(emergencyModelArrayList);
            recyclerView.setAdapter(adapter);
            Toast.makeText(getActivity(), msg + "", Toast.LENGTH_LONG).show();

            progress.dismiss();
        }
    }

    private void approveItem(final int position){

        // update emergency dstatus
        //Refreshpage
    }

    public void onItemClick(int p) {
        EmergencyModel item = (EmergencyModel) emergencyModelArrayList.get(p);
        Intent i = new Intent(getActivity(),ViewMapActivity.class);
        Bundle selectediteminformation = new Bundle();
        selectediteminformation.putString(EXTRA_EMERGENCYID,String.valueOf(item.getEmergencyid()));
        selectediteminformation.putString(EXTRA_EMERGENCYTYPE,item.getEmergencytype());
        selectediteminformation.putString(EXTRA_EMERGENCYSTATUS,item.getEmergencystatus());
        selectediteminformation.putString(EXTRA_EMERGENCYLOCATION,item.getEmergencylocation());
        selectediteminformation.putString(EXTRA_DATETIME,item.getDatetime());
        selectediteminformation.putString(EXTRA_LATITUDE,item.getLatitude());
        selectediteminformation.putString(EXTRA_LONGITUDE,item.getLongitude());

        i.putExtra(BUNDLE_EXTRAS,selectediteminformation);
        startActivity(i);
    }
}