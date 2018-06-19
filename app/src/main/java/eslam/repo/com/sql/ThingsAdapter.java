package eslam.repo.com.sql;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThingsAdapter extends RecyclerView.Adapter<ThingsAdapter.ThingsViewHolder> {

    int index = 100;
    int mColor;
    Context mContext;

    public ThingsAdapter(Context context) {
        mContext = context;
    }

    public interface OnListItemClickListener {
        void onPositionClicked(int position);
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

        holder.bind(position,"eslam");

    }

    @Override
    public int getItemCount() {
        return index;
    }

    public class ThingsViewHolder extends RecyclerView.ViewHolder {

        TextView mIndex;
        TextView mPostText;

        public ThingsViewHolder(View itemView) {
            super(itemView);

            mIndex = itemView.findViewById(R.id.index);
            mPostText = itemView.findViewById(R.id.post_text);
        }

        void bind(int index, String eslam) {
            mIndex.setText("" + index);
            mPostText.setText(eslam);
        }

    }
}
