package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.NavigationOption;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<DrawerRecyclerAdapter.DrawerRecyclerHolder> {
    private LayoutInflater mLayoutInflater;
    private List<NavigationOption> data = Collections.emptyList();

    public DrawerRecyclerAdapter(Context context, List<NavigationOption> data){
        mLayoutInflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public DrawerRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.navigation_drawer_row, parent, false);
        DrawerRecyclerHolder viewHolder = new DrawerRecyclerHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerRecyclerHolder holder, int position) {
        NavigationOption current = data.get(position);
        holder.navigationItemText.setText(current.title);
        holder.navigationItemIcon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DrawerRecyclerHolder extends RecyclerView.ViewHolder {
        private TextView navigationItemText;
        private ImageView navigationItemIcon;
        public DrawerRecyclerHolder(View itemView) {
            super(itemView);
            navigationItemText = (TextView) itemView.findViewById(R.id.nav_list_text);
            navigationItemIcon = (ImageView) itemView.findViewById(R.id.nav_list_icon);
        }
    }
}
