package eslam.repo.com.sql.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import eslam.repo.com.sql.data.ThingsContract.*;

public class ThingsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "things.db";

    public ThingsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_USERS_TABLE = " CREATE TABLE " +
                usersEntry.TABLE_NAME + "( " +
                usersEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                usersEntry.COLUMN_USER_NAME + " TEXT NOT NULL , " +
                usersEntry.COLUMN_USER_PASS + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_POSTS_TABLE = " CREATE TABLE " +
                postEntry.TABLE_NAME + "(" +
                postEntry.COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                postEntry.COLUMN_USER_ID + " INTEGER NOT NULL ," +
                postEntry.COLUMN_POST_TEXT + " TEXT NOT NULL " +
                ");";

       db.execSQL(SQL_CREATE_USERS_TABLE);
       db.execSQL(SQL_CREATE_POSTS_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }

}