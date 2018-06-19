package eslam.repo.com.sql;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PostsActivity extends AppCompatActivity {

    TextView userNameText;

    RecyclerView mRecyclerView;
    ThingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        userNameText = findViewById(R.id.user_name_text);
        userNameText.setText(userData());
        mRecyclerView = findViewById(R.id.list_of_things);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        adapter = new ThingsAdapter(this);

        mRecyclerView.setAdapter(adapter);
    }

    private String userData() {
        Intent i = getIntent();
        String userName = i.getExtras().getString("username");
        return userName;
    }
}
