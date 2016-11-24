package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.BuildConfig;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.User;
import pl.hypeapp.wykopolka.presenter.SignInPresenter;
import pl.hypeapp.wykopolka.view.SignInView;

public class SignInActivity extends CompositeActivity implements SignInView {
    private static final String APP_KEY = BuildConfig.APP_KEY;
    private static final String CONNECT_SUCCESS_URL = "https://a.wykop.pl/user/ConnectSuccess/";
    private static final String CONNECT_WYKOP_API_URL = "http://a.wykop.pl/user/connect/appkey/";
    private static final String WYKOP_API_URL = "a.wykop.pl";
    private SignInPresenter mSignInPresenter;
    @BindView(R.id.webview) WebView loginWebView;
    @BindView(R.id.tv_connect_success) TextView successLoginTextView;

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
        loginWebView.setWebViewClient(webClient);
        //Test on API > 18
        loginWebView.getSettings().setSavePassword(false);
        loginWebView.loadUrl(CONNECT_WYKOP_API_URL + APP_KEY);
    }

    @Override
    public void loginAndSaveUser(User user, String accountKey) {
        Log.e("SignInActivity", accountKey);
        Log.e("SignInActivity", user.getLogin());
        Log.e("SignInActivity", user.getAvatar());
        App.saveToPreferences(this, "user_account_key", accountKey);
        App.saveToPreferences(this, "user_login", user.getLogin());
        App.saveToPreferences(this, "user_avatar", user.getAvatarBig());
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public void showSuccessLoginInfo() {
        loginWebView.setVisibility(View.GONE);
        successLoginTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).toString().contains(WYKOP_API_URL)) {
                Log.e("WYKOP_LOGIN_URL", " " + Uri.parse(url).getHost());
                return false;
            }
            Log.e("WYKOP_LOGIN_URL false", " " + url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("WYKOP_LOGIN_URL webView", url);
            if (Uri.parse(url).toString().contains(CONNECT_SUCCESS_URL)) {
                mSignInPresenter.handleLogin(url);
            }
        }
    }
}
