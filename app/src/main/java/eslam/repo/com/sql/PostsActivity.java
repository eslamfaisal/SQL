package eslam.repo.com.sql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import eslam.repo.com.sql.data.ThingsContract.*;

public class PostsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

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
        getSupportLoaderManager().initLoader(1, null, this);
        adapter = new ThingsAdapter(this);

        mRecyclerView.setAdapter(adapter);

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

}
