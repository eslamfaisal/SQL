package eslam.repo.com.sql;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eslam.repo.com.sql.data.ThingsContract;

public class ThingsAdapter extends RecyclerView.Adapter<ThingsAdapter.ThingsViewHolder> {

    int index = 100;
    Context mContext;
    Cursor mCursor;

    public ThingsAdapter(Context context) {
        mContext = context;

    }


    @NonNull
    @Override
    public ThingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        boolean attached = false;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.things_list_item, parent, attached);
        return new ThingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThingsViewHolder holder, int position) {

        if (mCursor.moveToNext()) {
            String postText = mCursor.getString(mCursor.getColumnIndex(ThingsContract.postEntry.COLUMN_POST_TEXT));
            String userName = mCursor.getString(mCursor.getColumnIndex(ThingsContract.postEntry.COLUMN_USER_NAME));
            int id = mCursor.getInt(mCursor.getColumnIndex(ThingsContract.postEntry.COLUMN_POST_ID));
            holder.itemView.setTag(id);
            holder.bind(userName, postText);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public class ThingsViewHolder extends RecyclerView.ViewHolder {

        TextView mIndex;
        TextView mPostText;

        public ThingsViewHolder(View itemView) {
            super(itemView);

            mIndex = itemView.findViewById(R.id.index);
            mPostText = itemView.findViewById(R.id.post_text);
        }

        void bind(String userName, String postText) {
            mIndex.setText(userName);
            mPostText.setText(postText);
        }
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
