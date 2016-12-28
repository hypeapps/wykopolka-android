package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.Wave;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.BuildConfig;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.WykopUser;
import pl.hypeapp.wykopolka.presenter.SignInPresenter;
import pl.hypeapp.wykopolka.view.SignInView;

public class SignInActivity extends CompositeActivity implements SignInView {
    private static final String APP_KEY = BuildConfig.APP_KEY;
    private static final String CONNECT_SUCCESS_URL = "https://a.wykop.pl/user/ConnectSuccess/";
    private static final String CONNECT_WYKOP_API_URL = "http://a.wykop.pl/user/connect/appkey/";
    private static final String WYKOP_API_URL = "a.wykop.pl";
    private SignInPresenter mSignInPresenter;
    @BindView(R.id.webview) WebView mLoginWebView;
    @BindView(R.id.login_to_wykopolka_info) View mLoginToWykopolkaInfo;
    @BindView(R.id.spin_loading) ProgressBar mSpinLoading;

    private final TiActivityPlugin<SignInPresenter, SignInView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<SignInPresenter>() {
                @NonNull
                @Override
                public SignInPresenter providePresenter() {
                    return new SignInPresenter();
                }
            });

    public SignInActivity() {
        addPlugin(mPresenterPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        mSignInPresenter = mPresenterPlugin.getPresenter();
        initWebClient();
    }

    private void initWebClient() {
        WebClient webClient = new WebClient();
        mLoginWebView.setWebViewClient(webClient);
        mLoginWebView.getSettings().setSavePassword(false);
        mLoginWebView.loadUrl(CONNECT_WYKOP_API_URL + APP_KEY);
    }

    @Override
    public void loginAndSaveUser(WykopUser user, String accountKey) {
        App.saveToPreferences(this, "user_account_key", accountKey);
        App.saveToPreferences(this, "user_login", user.getLogin());
        App.saveToPreferences(this, "user_avatar", user.getAvatarBig());
        App.saveToPreferences(this, "user_login_status", true);
        Intent intentToDashboard = new Intent(this, DashboardActivity.class);
        intentToDashboard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentToDashboard);
        finish();
    }

    @Override
    public void showWykopolkaLoginInfo() {
        mLoginWebView.setVisibility(View.GONE);
        if (mLoginToWykopolkaInfo != null) {
            Wave wave = new Wave();
            mSpinLoading.setIndeterminateDrawable(wave);
            mLoginToWykopolkaInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void handleError() {
        App.clearPreferences(this);
        Intent intentBackToWelcome = new Intent(this, WelcomeActivity.class);
        intentBackToWelcome.putExtra("login_failed", true);
        startActivity(intentBackToWelcome);
        finish();
    }

    class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).toString().contains(WYKOP_API_URL)) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (Uri.parse(url).toString().contains(CONNECT_SUCCESS_URL)) {
                mSignInPresenter.handleWykopLogin(url);
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mSignInPresenter.onErrorHandling();
        }
    }
}
