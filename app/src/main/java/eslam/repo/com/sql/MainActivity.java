package eslam.repo.com.sql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eslam.repo.com.sql.data.ThingsContract;
import eslam.repo.com.sql.data.ThingsDbHelper;

public class MainActivity extends AppCompatActivity {
    EditText name, pass, name1, pass1;
    Button login, login1;

    ThingsDbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    public static final String USER_DATA_NAME = "user_data_name";
    public static final String USER_DATA_PASS = "user_data_pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.get_user_name);
        pass = findViewById(R.id.get_user_pass);
        login = findViewById(R.id.login);
        name1 = findViewById(R.id.put_user_name);
        pass1 = findViewById(R.id.put_user_pass);
        login1 = findViewById(R.id.signin);

        dbHelper = new ThingsDbHelper(this);
    }

    public void getData(View view) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        String uName = name.getText().toString().trim();
        String uPass = pass.getText().toString().trim();

        if (uName.length() <= 0 || uPass.length() <= 0) {
            return;
        } else {
            Toast.makeText(this, "" + query(uName, uPass), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,PostsActivity.class);
            intent.putExtra("username",uName);
            startActivity(intent);
        }


    }



    public void putData(View view) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        String uName = name1.getText().toString().trim();
        String uPass = pass1.getText().toString().trim();

        int exist= userNameQuery(uName);
        if (exist!=0){
            Toast.makeText(this, "this name is exist", Toast.LENGTH_SHORT).show();
        }else {
            ContentValues values = new ContentValues();
            values.put(ThingsContract.usersEntry.COLUMN_USER_NAME, uName);
            values.put(ThingsContract.usersEntry.COLUMN_USER_PASS, uPass);

            Uri lon =  getContentResolver().insert(ThingsContract.usersEntry.USERS_CONTENT_URI,values );
            if(lon!=null) {
                Toast.makeText(this, "post = " + lon.toString(), Toast.LENGTH_SHORT).show();

            }
        }

    }

    public int query(String name, String pass) {
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = ThingsContract.usersEntry.COLUMN_USER_NAME +
                " = ? AND " + ThingsContract.usersEntry.COLUMN_USER_PASS + " = ?";
        String[] selectionArgs = {name, pass};

        Cursor cursor = sqLiteDatabase.query(
                ThingsContract.usersEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null                    // The sort order
        );
        return cursor.getCount();

    }
    public int userNameQuery(String name) {
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = ThingsContract.usersEntry.COLUMN_USER_NAME +" = ?" ;
        String[] selectionArgs = {name};

        Cursor cursor = sqLiteDatabase.query(
                ThingsContract.usersEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null                    // The sort order
        );
        return cursor.getCount();
    }


}
