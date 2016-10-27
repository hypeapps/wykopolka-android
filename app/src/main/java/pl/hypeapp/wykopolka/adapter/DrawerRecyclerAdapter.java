package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.Collections;
import java.util.List;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.NavigationItem;
import pl.hypeapp.wykopolka.ui.fragment.NavigationDrawerFragment;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<DrawerRecyclerAdapter.DrawerRecyclerHolder> {
    private LayoutInflater mLayoutInflater;
    private List<NavigationItem> mDataSet = Collections.emptyList();
    private NavigationDrawerFragment.ClickItemHandler clickItemHandler;

    public DrawerRecyclerAdapter(Context context, List<NavigationItem> dataSet, NavigationDrawerFragment.ClickItemHandler clickItemHandler) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mDataSet = dataSet;
        this.clickItemHandler = clickItemHandler;
    }

    @Override
    public DrawerRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.navigation_drawer_row, parent, false);
        DrawerRecyclerHolder viewHolder = new DrawerRecyclerHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerRecyclerHolder holder, int position) {
        NavigationItem current = mDataSet.get(position);
        holder.navigationItemText.setText(current.title);
        holder.navigationItemIcon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class DrawerRecyclerHolder extends RecyclerView.ViewHolder implements RippleView.OnRippleCompleteListener {
        private TextView navigationItemText;
        private ImageView navigationItemIcon;

        public DrawerRecyclerHolder(View itemView) {
            super(itemView);
            navigationItemText = (TextView) itemView.findViewById(R.id.nav_list_text);
            navigationItemIcon = (ImageView) itemView.findViewById(R.id.nav_list_icon);
            RippleView rippleView = (RippleView) itemView.findViewById(R.id.nav_ripple);
            rippleView.setOnRippleCompleteListener(this);
        }

        @Override
        public void onComplete(RippleView rippleView) {
            clickItemHandler.handleIntent(getLayoutPosition());
        }
    }
}