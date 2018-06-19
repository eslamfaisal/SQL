package eslam.repo.com.sql;

import android.content.ContentValues;
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
    public static String USER_NAME = "user_data_name";
    public static int USER_ID = 0;

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

    public void signIn(View view) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        String uName = name.getText().toString().trim();
        String uPass = pass.getText().toString().trim();

        if (uName.length() <= 0 || uPass.length() <= 0) {
            return;
        } else {
            userData(uName);
        }
    }

    public void signUp(View view) {

        sqLiteDatabase = dbHelper.getWritableDatabase();
        String uName = name1.getText().toString().trim();
        String uPass = pass1.getText().toString().trim();

        int exist = checkUserName(uName).getCount();
        if (exist != 0) {
            Toast.makeText(this, "this name is exists", Toast.LENGTH_SHORT).show();
        } else {
            insertUser(uName, uPass);
        }

    }

    public void userData(String name) {
        Cursor cursor = checkUserName(name);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "this name is not exists", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToNext()) {
                int userId = cursor.getInt(cursor.getColumnIndex(ThingsContract.usersEntry.COLUMN_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndex(ThingsContract.usersEntry.COLUMN_USER_NAME));
                USER_ID = userId;
                USER_NAME = userName;
            }
        }
    }


    public void insertUser(String name, String pass) {
        ContentValues values = new ContentValues();
        values.put(ThingsContract.usersEntry.COLUMN_USER_NAME, name);
        values.put(ThingsContract.usersEntry.COLUMN_USER_PASS, pass);

        Uri lon = getContentResolver().insert(ThingsContract.usersEntry.USERS_CONTENT_URI, values);
        if (lon != null) {
            Toast.makeText(this, "post = " + lon.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    public Cursor checkUserName(String name) {
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = ThingsContract.usersEntry.COLUMN_USER_NAME + " = ?";
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
        return cursor;
    }


}
