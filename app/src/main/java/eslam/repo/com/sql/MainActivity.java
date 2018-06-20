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

    public static final String USER_NAME_INTENT = "USER_NAME";
    public static final String USER_ID_INTENT = "USER_ID";

    public static String USER_NAME;
    public static int USER_ID;

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

        if (!(uName.length() <= 0 || uPass.length() <= 0)) {
            userData(uName, uPass);
        }
    }

    public void signUp(View view) {

        sqLiteDatabase = dbHelper.getWritableDatabase();
        String uName = name1.getText().toString().trim();
        String uPass = pass1.getText().toString().trim();

        if (!(uName.length() <= 0 || uPass.length() <= 0)) {
            int exist = checkUserName(uName).getCount();
            if (exist != 0) {
                Toast.makeText(this, "الاسم دا موجود اديني واحد غيرة", Toast.LENGTH_SHORT).show();
            } else {
                insertUser(uName, uPass);
            }
        }else {
            Toast.makeText(this, "دخل الاسم والباسورد يعم", Toast.LENGTH_SHORT).show();
        }
    }

    public void userData(String name, String pass) {
        Cursor cursor = checkUserNameAndPass(name, pass);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "اتاكد من الاسم والباسورد يعم ", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToNext()) {
                int userId = cursor.getInt(cursor.getColumnIndex(ThingsContract.usersEntry.COLUMN_USER_ID));
                String userName = cursor.getString(cursor.getColumnIndex(ThingsContract.usersEntry.COLUMN_USER_NAME));
                USER_ID = userId;
                USER_NAME = userName;
                Intent intent = new Intent(this, PostsActivity.class);
                intent.putExtra(USER_NAME_INTENT, USER_NAME);
                intent.putExtra(USER_ID_INTENT, USER_ID);
                startActivity(intent);
                finish();
            }
        }
    }

    public void insertUser(String name, String pass) {
        ContentValues values = new ContentValues();
        values.put(ThingsContract.usersEntry.COLUMN_USER_NAME, name);
        values.put(ThingsContract.usersEntry.COLUMN_USER_PASS, pass);

        Uri lon = getContentResolver().insert(ThingsContract.usersEntry.USERS_CONTENT_URI, values);
        if (lon != null) {
            Toast.makeText(this, "اتضاف ", Toast.LENGTH_SHORT).show();

        }
    }

    public Cursor checkUserName(String name) {
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = ThingsContract.usersEntry.COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = {name};

        Cursor cursor = getContentResolver().query(ThingsContract.usersEntry.USERS_CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        return cursor;
    }

    public Cursor checkUserNameAndPass(String name, String pass) {
        sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = ThingsContract.usersEntry.COLUMN_USER_NAME + " = ? AND " +
                ThingsContract.usersEntry.COLUMN_USER_PASS + " = ?";
        String[] selectionArgs = {name, pass};

        Cursor cursor = getContentResolver().query(ThingsContract.usersEntry.USERS_CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        return cursor;
    }
}
