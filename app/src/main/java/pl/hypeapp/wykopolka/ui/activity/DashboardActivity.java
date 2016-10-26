package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.plugin.NavigationDrawerActivityPlugin;
import pl.hypeapp.wykopolka.presenter.DashboardPresenter;
import pl.hypeapp.wykopolka.view.DashboardView;

public class DashboardActivity extends CompositeActivity implements DashboardView {
    private final NavigationDrawerActivityPlugin mNavigationDrawerPlugin = new NavigationDrawerActivityPlugin();
    private final TiActivityPlugin<DashboardPresenter, DashboardView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<DashboardPresenter>() {
                @NonNull
                @Override
                public DashboardPresenter providePresenter() {
                    return new DashboardPresenter();
                }
            });

    public DashboardActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mNavigationDrawerPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = initToolbar();
        mNavigationDrawerPlugin.setNavigationDrawer(toolbar);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;
    }

}
