package eslam.repo.com.sql;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;

import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import eslam.repo.com.sql.data.ThingsContract;

public class AllPostsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView mRecyclerView;
    ThingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);
        mRecyclerView = findViewById(R.id.all_posts_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        getSupportLoaderManager().initLoader(1,null,this);
        adapter = new ThingsAdapter(this);

        mRecyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                ThingsContract.postEntry.COLUMN_POST_ID,
                ThingsContract.postEntry.COLUMN_POST_TEXT,
                ThingsContract.postEntry.COLUMN_USER_NAME,
        };
        String selection = ThingsContract.postEntry.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {""+MainActivity.USER_ID};

        return new CursorLoader(this, ThingsContract.postEntry.POST_CONTENT_URI, projection,
                null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {

    }
}
