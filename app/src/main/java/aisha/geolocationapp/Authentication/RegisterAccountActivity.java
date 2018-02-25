package aisha.geolocationapp.Authentication;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.ContentValues;
import aisha.geolocationapp.Database.SQLiteDBHelper;
import aisha.geolocationapp.R;

/**
 * Created by UkoDavid on 02/10/2017.
 */

public class RegisterAccountActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        //To hide AppBar for fullscreen.
        ActionBar ab = getSupportActionBar();
        ab.hide();

        openHelper = new SQLiteDBHelper(this);

        //Referencing EditText widgets and Button placed inside in xml layout file
        final EditText _txtfullname = (EditText) findViewById(R.id.txtname_reg);
        final EditText _txtemail = (EditText) findViewById(R.id.txtemail_reg);
        final EditText _txtpass = (EditText) findViewById(R.id.txtpass_reg);
        Button _btnreg = (Button) findViewById(R.id.btn_reg);

        //Register Button Click Event
        _btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = openHelper.getWritableDatabase();

                String fullname = _txtfullname.getText().toString();
                String email = _txtemail.getText().toString();
                String pass = _txtpass.getText().toString();

                //Calling InsertData Method - Defined below
                InsertData(fullname, email, pass);

                //Alert dialog after clicking the Register Account
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAccountActivity.this);
                builder.setTitle("Information");
                builder.setMessage("Your Account is Successfully Created.");
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Finishing the dialog and removing Activity from stack.
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    //Inserting Data into database - Like INSERT INTO QUERY.
    public void InsertData(String fullName, String email, String password ) {

        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_FULLNAME,fullName);
        values.put(SQLiteDBHelper.COLUMN_EMAIL,email);
        values.put(SQLiteDBHelper.COLUMN_PASSWORD,password);
        long id = db.insert(SQLiteDBHelper.USERTABLE_NAME,null,values);

    }

}
