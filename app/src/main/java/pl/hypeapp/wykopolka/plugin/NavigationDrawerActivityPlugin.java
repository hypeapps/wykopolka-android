package pl.hypeapp.wykopolka.plugin;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.pascalwelsch.compositeandroid.activity.ActivityPlugin;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.ui.fragment.NavigationDrawerFragment;

public class NavigationDrawerActivityPlugin extends ActivityPlugin {

    public void setNavigationDrawer(Toolbar toolbar){
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
              getCompositeDelegate().getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerFragment.create(drawerLayout, toolbar, R.id.fragment_navigation_drawer);
    }
}
