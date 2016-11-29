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
import pl.hypeapp.wykopolka.presenter.ProfileSettingsPresenter;
import pl.hypeapp.wykopolka.view.ProfileSettingsView;

public class ProfileSettingsActivity extends CompositeActivity implements ProfileSettingsView {
    @BindView(R.id.toolbar) Toolbar mToolbar;


    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();

    private final TiActivityPlugin<ProfileSettingsPresenter, ProfileSettingsView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<ProfileSettingsPresenter>() {
                @NonNull
                @Override
                public ProfileSettingsPresenter providePresenter() {
                    return new ProfileSettingsPresenter();
                }
            });


    public ProfileSettingsActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        ButterKnife.bind(this);
        mToolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(mToolbar);
    }

}