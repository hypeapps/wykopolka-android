package pl.hypeapp.wykopolka.plugin;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.pascalwelsch.compositeandroid.activity.ActivityPlugin;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.ui.fragment.NavigationDrawerFragment;

public class ToolbarActivityPlugin extends ActivityPlugin {
    private static final String EMPTY_STRING = "";
    private Toolbar mToolbar;

    public void setNavigationDrawer(Toolbar toolbar){
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
              getCompositeDelegate().getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerFragment.create(drawerLayout, toolbar, R.id.fragment_navigation_drawer);
    }

    public Toolbar initToolbar(Toolbar toolbar){
        mToolbar = toolbar;
        getCompositeDelegate().setSupportActionBar(mToolbar);
        ActionBar actionBar = getCompositeDelegate().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;
    }

    public Toolbar initToolbarWithEmptyTitle(Toolbar toolbar) {
        toolbar.setTitle(EMPTY_STRING);
        return initToolbar(toolbar);
    }
}
