package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.DashboardPresenter;
import pl.hypeapp.wykopolka.view.DashboardView;

public class DashboardActivity extends CompositeActivity implements DashboardView {
    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();
    private final TiActivityPlugin<DashboardPresenter, DashboardView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<DashboardPresenter>() {
                @NonNull
                @Override
                public DashboardPresenter providePresenter() {
                    return new DashboardPresenter();
                }
            });
    @BindView(R.id.toolbar) Toolbar mToolbar;

    public DashboardActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        Toolbar toolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(toolbar);
    }

}
