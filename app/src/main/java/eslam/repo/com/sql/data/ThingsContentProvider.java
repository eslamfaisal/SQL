package eslam.repo.com.sql.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import eslam.repo.com.sql.data.ThingsContract.*;

public class ThingsContentProvider extends ContentProvider {

    ThingsDbHelper dbHelper;
    public static final int POST = 1;
    public static final int POST_ID = 2;
    public static final int USERS = 4;
    public static final int USRES_ID = 3;

    public UriMatcher matcher = matcher();

    public static UriMatcher matcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ThingsContract.AUTHORITY, ThingsContract.postEntry.PATH_POSTS, POST);
        uriMatcher.addURI(ThingsContract.AUTHORITY, ThingsContract.postEntry.PATH_POSTS + "/#", POST_ID);
        uriMatcher.addURI(ThingsContract.AUTHORITY, ThingsContract.usersEntry.PATH_USERS, USERS);
        uriMatcher.addURI(ThingsContract.AUTHORITY, ThingsContract.usersEntry.PATH_USERS + "/#", USRES_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ThingsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int match = matcher.match(uri);
        Uri returnUri;
        long id;
        switch (match) {
            case POST:
                id = database.insert(postEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(postEntry.USERS_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("fail to insert" + uri);
                }
                break;
            case USERS:
                id = database.insert(usersEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(usersEntry.USERS_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("fail to insert" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("unknown uri :" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
