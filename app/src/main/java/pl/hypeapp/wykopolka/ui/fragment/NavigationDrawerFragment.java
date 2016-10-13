package pl.hypeapp.wykopolka.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.DrawerRecyclerAdapter;
import pl.hypeapp.wykopolka.model.NavigationOption;
import pl.hypeapp.wykopolka.presenter.NavigationDrawerPresenter;
import pl.hypeapp.wykopolka.ui.activity.MainWallActivity;
import pl.hypeapp.wykopolka.util.transformation.CropCircleTransformation;
import pl.hypeapp.wykopolka.view.NavigationDrawerView;

public class NavigationDrawerFragment extends TiFragment<NavigationDrawerPresenter, NavigationDrawerView>
        implements NavigationDrawerView {
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View mContainer;
    private boolean mDrawerOpened = false;
    private DrawerRecyclerAdapter mRecyclerAdapter;

    RecyclerView mRecyclerView;
    @BindView(R.id.iv_avatar)
    ImageView avatar;

    public NavigationDrawerFragment() {
    }

    @NonNull
    @Override
    public NavigationDrawerPresenter providePresenter() {
        return new NavigationDrawerPresenter();
    }

    public static List<NavigationOption> getData(){
        List<NavigationOption> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_exit_to_app_white_36dp, R.drawable.ic_exit_to_app_white_36dp, R.drawable.ic_exit_to_app_white_36dp, R.drawable.ic_exit_to_app_white_36dp
        , R.drawable.ic_exit_to_app_white_36dp, R.drawable.ic_exit_to_app_white_36dp, R.drawable.ic_exit_to_app_white_36dp, R.drawable.ic_exit_to_app_white_36dp, R.drawable.ic_exit_to_app_white_36dp
        ,R.drawable.ic_exit_to_app_white_36dp};
        String[] titles = {"Dashboard","Szukaj książki","Losuj książkę", "Moje książki", "Moja historia", "Oddane książki"
        ,"Lista życzeń", "Ranking i statystyki"};
        for(int i = 0; i < titles.length && i < icons.length; i++){
            NavigationOption current = new NavigationOption();
            current.title = titles[i];
            current.iconId = icons[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = App.readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, false);
        mFromSavedInstanceState = savedInstanceState != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.drawer_list);
        mRecyclerAdapter = new DrawerRecyclerAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getContext()).load(R.drawable.avatar).bitmapTransform(new CropCircleTransformation(getContext())).into(avatar);
    }

    public void create(DrawerLayout drawerLayout, final Toolbar toolbar, int fragmentId) {
        mContainer = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d("OnNavgationFrag", "onDrawerOpened");
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    App.saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer);
                }
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("VIVZ", "onDrawerClosed");
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
//                ((AddedBooksActivity) getActivity()).onDrawerSlide(slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if (!mFromSavedInstanceState) {
                    mDrawerLayout.openDrawer(mContainer);
                    if (getActivity() instanceof MainWallActivity && !mUserLearnedDrawer) {
                        mDrawerLayout.openDrawer(mContainer);
                    }
                }
            }
        });
    }
}
