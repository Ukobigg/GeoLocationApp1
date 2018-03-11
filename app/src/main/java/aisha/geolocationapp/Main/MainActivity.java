package aisha.geolocationapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import aisha.geolocationapp.R;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Welcome name and email
        TextView txtname = (TextView) findViewById(R.id.Welcome_name);
        TextView txtemail = (TextView) findViewById(R.id.Welcome_email);
        TextView txtuserid = (TextView) findViewById(R.id.User_id);

        Intent intent = getIntent();

        String loginName = intent.getStringExtra("fullname");
        String loginEmail = intent.getStringExtra("email");

        txtname.setText("Welcome, " +loginName);
        txtemail.setText(loginEmail);
        txtuserid.setText(loginName);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        String title ="";
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                selectedFragment = NewrequestFragment.newInstance();
                                title = "Home";
                                break;

                            case R.id.navigation_dashboard:
                                selectedFragment = NotificationFragment.newInstance();
                                title = "All Emergencies";
                                break;

                            case R.id.navigation_notifications:
                                selectedFragment = AboutusFragment.newInstance();
                                title = "About Us";
                                break;

                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, selectedFragment);
                        transaction.commit();
                        getSupportActionBar().setTitle(title);
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, NewrequestFragment.newInstance());
        transaction.commit();
        getSupportActionBar().setTitle("Home");


        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }

}
