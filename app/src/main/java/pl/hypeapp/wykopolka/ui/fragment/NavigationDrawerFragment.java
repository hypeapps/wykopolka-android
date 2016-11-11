package pl.hypeapp.wykopolka.ui.fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.DrawerRecyclerAdapter;
import pl.hypeapp.wykopolka.model.NavigationItem;
import pl.hypeapp.wykopolka.presenter.NavigationDrawerPresenter;
import pl.hypeapp.wykopolka.ui.activity.AddedBooksActivity;
import pl.hypeapp.wykopolka.ui.activity.DashboardActivity;
import pl.hypeapp.wykopolka.view.NavigationDrawerView;

public class NavigationDrawerFragment extends TiFragment<NavigationDrawerPresenter, NavigationDrawerView>
        implements NavigationDrawerView {
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private static String[] navigationDrawerTitles;
    private static TypedArray navigationDrawerIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View mContainer;
    private boolean mDrawerOpened = false;
    private DrawerRecyclerAdapter mRecyclerAdapter;
    private String mAvatarUrl;
    private String mUserLogin;
    @BindView(R.id.drawer_list)
    RecyclerView mRecyclerView;

    public NavigationDrawerFragment() {
    }

    @NonNull
    @Override
    public NavigationDrawerPresenter providePresenter() {
        return new NavigationDrawerPresenter();
    }

    public static List<NavigationItem> getNavigationItems() {
        List<NavigationItem> data = new ArrayList<>();
        TypedArray icons = navigationDrawerIcons;
        String[] titles = navigationDrawerTitles;

        for (int i = 0; i < titles.length && i < icons.length(); i++) {
            NavigationItem current = new NavigationItem();
            current.title = titles[i];
            current.iconId = icons.getResourceId(i, 0);
            data.add(current);
        }
        return data;
    }

    public Map<String, String> getUserData() {
        Map<String, String> userData = new HashMap<>();
        userData.put("user_login", mUserLogin);
        userData.put("user_avatar", mAvatarUrl);
        return userData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = App.readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, false);
        mAvatarUrl = App.readFromPreferences(getActivity(), "user_avatar", "");
        mUserLogin = App.readFromPreferences(getActivity(), "user_login", "unknown");
        mFromSavedInstanceState = savedInstanceState != null;
        navigationDrawerIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icons);
        navigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ButterKnife.bind(this, view);
        mRecyclerAdapter = new DrawerRecyclerAdapter(getActivity(), getNavigationItems(), getUserData(), new ClickItemHandler());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        return view;
    }

    public void create(DrawerLayout drawerLayout, final Toolbar toolbar, int fragmentId) {
        mContainer = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    App.saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer);
                }
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
//                ((AddedBooksActivity) getActivity()).onDrawerSlide(slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                    mDrawerLayout.openDrawer(mContainer);
                }
            }
        });
    }

    public class ClickItemHandler {

        public void handleIntent(final int position) {
            mDrawerLayout.closeDrawers();
            mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    startIntentActivity(position);
                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });

        }

        private void startIntentActivity(int position) {
            switch (position) {
                case 0:
                    startActivity(new Intent(getActivity(), DashboardActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(getActivity(), AddedBooksActivity.class));
                    break;
            }
        }
    }
}
