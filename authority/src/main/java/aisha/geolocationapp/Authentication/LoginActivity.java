package aisha.geolocationapp.Authentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import aisha.geolocationapp.Database.SQLiteDBHelper;
import aisha.geolocationapp.Main.MainActivity;
import aisha.geolocationapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //To hide AppBar for fullscreen.
        ActionBar ab = getSupportActionBar();
        ab.hide();

        //Referencing UserEmail, Password EditText and TextView for SignUp Now
        final EditText _txtemail = (EditText) findViewById(R.id.txtemail);
        final EditText _txtpass = (EditText) findViewById(R.id.txtpass);
        Button btnlogin = (Button) findViewById(R.id.btnsignin);
        TextView btnregister = (TextView) findViewById(R.id.btnreg1);

        //Opening SQLite Pipeline
        dbhelper = new SQLiteDBHelper(this);
        db = dbhelper.getReadableDatabase();

        btnlogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = _txtemail.getText().toString();
                String pass = _txtpass.getText().toString();
                String sql = String.format("SELECT *FROM "+SQLiteDBHelper.USERTABLE_NAME+" WHERE "+SQLiteDBHelper.COLUMN_EMAIL+"=? AND "+SQLiteDBHelper.COLUMN_PASSWORD+"=?");

                cursor = db.rawQuery(sql,new String[] { email, pass});
                if (cursor != null) {
                    if(cursor.getCount() > 0) {

                        cursor.moveToFirst();
                        //Retrieving User FullName and Email after successfull login and passing to LoginSucessActivity
                        String _fname = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_FULLNAME));
                        String _email= cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EMAIL));
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("fullname",_fname);
                        intent.putExtra("email",_email);
                        startActivity(intent);

                        //Removing MainActivity[Login Screen] from the stack for preventing back button press.
                        finish();
                    }
                    else {

                        //I am showing Alert Dialog Box here for alerting user about wrong credentials
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Alert");
                        builder.setMessage("Username or Password is wrong.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        //-------Alert Dialog Code Snippet End Here
                    }
                }

            }
        });

        // Intent For Opening com.google.android.gms.location.sample.basiclocationsample.RegisterAccountActivity
        btnregister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}
