package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.RankPosition;

public class TopUsersRecyclerAdapter extends RecyclerView.Adapter<TopUsersRecyclerAdapter.TopUsersRecyclerHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<RankPosition> mDataSet = Collections.emptyList();

    public TopUsersRecyclerAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TopUsersRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.rank_position_row, parent, false);
        return new TopUsersRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(TopUsersRecyclerHolder holder, int position) {
        RankPosition current = mDataSet.get(position);
        holder.nickname.setText(current.getNickname());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(List<RankPosition> books) {
        mDataSet = books;
        notifyDataSetChanged();
    }

    class TopUsersRecyclerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rank_position) TextView position;
        @BindView(R.id.tv_rank_nickname) TextView nickname;

        public TopUsersRecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
