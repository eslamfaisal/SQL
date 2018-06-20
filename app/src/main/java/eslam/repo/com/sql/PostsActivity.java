package eslam.repo.com.sql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import eslam.repo.com.sql.data.ThingsContract;
import eslam.repo.com.sql.data.ThingsContract.*;

public class PostsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TASK_LOADER_ID = 1;
    int userID;
    RecyclerView mRecyclerView;
    ThingsAdapter adapter;

    EditText editTextPoet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPost();
                editTextPoet.getText().clear();
            }
        });

        Intent i = getIntent();
        String userName = i.getExtras().getString(MainActivity.USER_NAME_INTENT);
        setTitle(userName);
        userID = i.getExtras().getInt(MainActivity.USER_ID_INTENT);

        mRecyclerView = findViewById(R.id.list_of_things);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
        adapter = new ThingsAdapter(this);
        mRecyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int id = (int) viewHolder.itemView.getTag();

                String stringId = Integer.toString(id);
                Uri uri = postEntry.POST_CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();

                getContentResolver().delete(uri, null, null);

                getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, PostsActivity.this);

            }
        }).attachToRecyclerView(mRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.all, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.all_posts_menu) {
            Intent intent = new Intent(this, AllPostsActivity.class);
            startActivity(intent);
        }else if (id == R.id.log_out){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.delete_user){
            deleteCurrentUser();
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                postEntry.COLUMN_POST_ID,
                postEntry.COLUMN_POST_TEXT,
                postEntry.COLUMN_USER_NAME,
        };
        String selection = postEntry.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {"" + MainActivity.USER_ID};

        return new CursorLoader(this, postEntry.POST_CONTENT_URI, projection,
                selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {

    }

    void addPost() {
        editTextPoet = findViewById(R.id.add_post);
        String postText = editTextPoet.getText().toString().trim();
        if (postText.length() > 0) {
            ContentValues values = new ContentValues();
            values.put(postEntry.COLUMN_POST_TEXT, postText);
            values.put(postEntry.COLUMN_USER_NAME, MainActivity.USER_NAME);
            values.put(postEntry.COLUMN_USER_ID, MainActivity.USER_ID);
            getContentResolver().insert(postEntry.POST_CONTENT_URI, values);
        }
    }

    void deleteCurrentUser(){
        Uri uri = usersEntry.USERS_CONTENT_URI;
        uri = uri.buildUpon().appendPath(String.valueOf(MainActivity.USER_ID)).build();
        getContentResolver().delete(uri, null, null);

        getContentResolver().delete(postEntry.POST_CONTENT_URI,postEntry.COLUMN_USER_ID+"=?",
                new String[]{String.valueOf(MainActivity.USER_ID)});
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
