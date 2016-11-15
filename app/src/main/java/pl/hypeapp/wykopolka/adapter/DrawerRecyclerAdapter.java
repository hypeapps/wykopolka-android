package pl.hypeapp.wykopolka.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.NavigationItem;
import pl.hypeapp.wykopolka.ui.fragment.NavigationDrawerFragment;
import pl.hypeapp.wykopolka.util.transformation.CropCircleTransformation;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<NavigationItem> navigationItems = Collections.emptyList();
    private Map<String, String> userData = Collections.emptyMap();
    private NavigationDrawerFragment.ClickItemHandler clickItemHandler;

    public DrawerRecyclerAdapter(Context context, List<NavigationItem> navigationItems,
                                 Map<String, String> userData, NavigationDrawerFragment.ClickItemHandler clickItemHandler) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.userData = userData;
        this.navigationItems = navigationItems;
        this.clickItemHandler = clickItemHandler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = mLayoutInflater.inflate(R.layout.naviagtion_drawer_header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = mLayoutInflater.inflate(R.layout.navigation_drawer_row, parent, false);
            return new ItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.username.setText("@" + userData.get("user_login"));
            Glide.with(mContext)
                    .load(userData.get("user_avatar"))
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(headerHolder.avatar);
        } else {
            ItemHolder itemHolder = (ItemHolder) holder;
            NavigationItem current = navigationItems.get(position - 1);
            itemHolder.navigationItemText.setText(current.title);
            itemHolder.navigationItemIcon.setImageResource(current.iconId);
        }
    }

    @Override
    public int getItemCount() {
        return navigationItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.nav_list_text)
        TextView navigationItemText;
        @BindView(R.id.nav_list_icon)
        ImageView navigationItemIcon;
        @BindView(R.id.ripple)
        MaterialRippleLayout materialRippleLayout;

        ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            materialRippleLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickItemHandler.handleIntent(getLayoutPosition() - 1);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_username)
        TextView username;
        @BindView(R.id.iv_avatar)
        ImageView avatar;

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
