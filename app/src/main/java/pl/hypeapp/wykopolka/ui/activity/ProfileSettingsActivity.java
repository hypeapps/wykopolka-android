package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.ProfileSettingsPresenter;
import pl.hypeapp.wykopolka.util.WebViewUtil;
import pl.hypeapp.wykopolka.view.ProfileSettingsView;

public class ProfileSettingsActivity extends CompositeActivity implements ProfileSettingsView {
    private ProfileSettingsPresenter mProfileSettingsPresenter;
    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.settings_layout) View parentLayout;
    @BindView(R.id.et_settings_fullname) EditText mSettingsFullName;
    @BindView(R.id.et_settings_address) EditText mSettingsAddress;
    @BindView(R.id.et_settings_postal_code) EditText mSettingsPostalCode;
    @BindView(R.id.et_settings_city) EditText mSettingsCity;
    @BindView(R.id.et_settings_address_email) EditText mSettingsEmail;
    @BindView(R.id.profile_settings) LinearLayout mProfileSettingsLayout;
    @BindView(R.id.switch_notification_email) Switch mSwitchNotificationEmail;
    @BindView(R.id.switch_notification_pm) Switch mSwitchNotificationPm;
    @BindView(R.id.error_view) View mErrorView;
    @BindView(R.id.loading_view) View mLoadingView;

    private final TiActivityPlugin<ProfileSettingsPresenter, ProfileSettingsView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<ProfileSettingsPresenter>() {
                @NonNull
                @Override
                public ProfileSettingsPresenter providePresenter() {
                    String accountKey = App.readFromPreferences(ProfileSettingsActivity.this, "user_account_key", null);
                    mProfileSettingsPresenter = new ProfileSettingsPresenter(accountKey);
                    return mProfileSettingsPresenter;
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
        mProfileSettingsPresenter = mPresenterPlugin.getPresenter();
        mToolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(mToolbar);
    }

    @Override
    public void setProfileAddress(User user) {
        mSettingsFullName.setText(user.getFullName());
        mSettingsAddress.setText(user.getAdress());
        mSettingsCity.setText(user.getCity());
        mSettingsPostalCode.setText(user.getPostal());
    }

    @Override
    public void setProfileEmail(User user) {
        mSettingsEmail.setText(user.getMail());
    }

    @Override
    public void setEmailNotificationsOn() {
        mSwitchNotificationEmail.setChecked(true);
    }

    @Override
    public void setEmailNotificationsOff() {
        mSwitchNotificationEmail.setChecked(false);
    }

    @Override
    public void setEmailNotificationsEnabled() {
        mSwitchNotificationEmail.setEnabled(true);
    }

    @Override
    public void setEmailNotificationsDisabled() {
        mSwitchNotificationEmail.setEnabled(false);
    }

    @Override
    public void setPmNotificationsOn() {
        mSwitchNotificationPm.setChecked(true);
    }

    @Override
    public void setPmNotificationsOff() {
        mSwitchNotificationPm.setChecked(false);
    }

    @Override
    @OnClick(R.id.btn_save_settings)
    public void submitSettings() {
        mProfileSettingsPresenter.submitSettings();
    }

    @Override
    public List<String> getProfileAddress() {
        List<String> profileAddress = new ArrayList<>();
        profileAddress.add(mSettingsFullName.getText().toString().trim());
        profileAddress.add(mSettingsAddress.getText().toString().trim());
        profileAddress.add(mSettingsPostalCode.getText().toString().trim());
        profileAddress.add(mSettingsCity.getText().toString().trim());
        return profileAddress;
    }

    @Override
    public String getProfileEmail() {
        return mSettingsEmail.getText().toString().trim();
    }

    @Override
    public boolean getCheckedEmail() {
        return mSwitchNotificationEmail.isChecked();
    }

    @Override
    public boolean getCheckedPm() {
        return mSwitchNotificationPm.isChecked();
    }

    @Override
    public void showMessageAddressIncorrect() {
        Snackbar.make(parentLayout, R.string.error_message_wrong_address, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMessageAddressEmailIncorrect() {
        Snackbar.make(parentLayout, R.string.error_message_wrong_email, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMessageSaveSettingsFail() {
        Snackbar.make(parentLayout, R.string.error_message_default, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMessageSaveSettingsSuccess() {
        Snackbar.make(parentLayout, R.string.success_message_user_update, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSettingsCard() {
        if (mProfileSettingsLayout != null) {
            mProfileSettingsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideSettingsCard() {
        if (mProfileSettingsLayout != null) {
            mProfileSettingsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideError() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick(R.id.btn_error_retry)
    public void retry() {
        mProfileSettingsPresenter.retryLoadProfileSettings();
    }

    @Override
    @OnClick(R.id.btn_logout)
    public void logout() {
        App.clearPreferences(this);
        WebViewUtil.clearCookies(this);
        Intent intentToWelcomeScreen = new Intent(this, WelcomeActivity.class);
        intentToWelcomeScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentToWelcomeScreen);
    }

}