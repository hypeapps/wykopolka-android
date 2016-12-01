package pl.hypeapp.wykopolka.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.StatsAndTopUsersPagerAdapter;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.StatsAndTopUsersPresenter;
import pl.hypeapp.wykopolka.view.StatsAndTopUsersView;

public class StatsAndTopUsersActivity extends CompositeActivity implements StatsAndTopUsersView {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.viewpager_tab) SmartTabLayout mSmartTabLayout;

    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();

    private final TiActivityPlugin<StatsAndTopUsersPresenter, StatsAndTopUsersView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<StatsAndTopUsersPresenter>() {
                @NonNull
                @Override
                public StatsAndTopUsersPresenter providePresenter() {
                    return new StatsAndTopUsersPresenter();
                }
            });

    public StatsAndTopUsersActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_and_top_users);
        ButterKnife.bind(this);
        mToolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(mToolbar);
        initViewPager(mViewPager, mSmartTabLayout);
    }

    private void initViewPager(ViewPager viewPager, SmartTabLayout smartTabLayout) {
        StatsAndTopUsersPagerAdapter bookPanelPagerAdapter = new StatsAndTopUsersPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(bookPanelPagerAdapter);

        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        final Resources resources = getResources();
        smartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View itemView = layoutInflater.inflate(R.layout.tab_icon_text, container, false);
                TextView text = (TextView) itemView.findViewById(R.id.custom_tab_text);
                text.setAllCaps(true);
                ImageView icon = (ImageView) itemView.findViewById(R.id.custom_tab_icon);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(resources.getDrawable(R.drawable.ic_book_white_36dp));
                        text.setText(getString(R.string.tab_title_statistic));
                        break;
                    case 1:
                        icon.setImageDrawable(resources.getDrawable(R.drawable.ic_added_book_white_36dp));
                        text.setText(getString(R.string.tab_title_ranking));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return itemView;
            }
        });
        smartTabLayout.setViewPager(viewPager);
    }
}