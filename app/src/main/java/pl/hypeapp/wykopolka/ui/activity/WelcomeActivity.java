package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.WelcomePagerAdapter;
import pl.hypeapp.wykopolka.extra.pagetransformer.StackTransformer;
import pl.hypeapp.wykopolka.presenter.WelcomePresenter;
import pl.hypeapp.wykopolka.view.WelcomeView;

public class WelcomeActivity extends CompositeActivity implements WelcomeView {
    public static final int LOGIN_PAGE = 2;
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.flexibleIndicator) ExtensiblePageIndicator mFlexibleIndicator;
    @BindView(R.id.iv_next) ImageView mNextPageButton;
    @BindView(R.id.welcome_parent_layout) View parentLayout;
    private WelcomePresenter mWelcomePresenter;

    public WelcomeActivity() {
        addPlugin(mPresenterPlugin);
    }

    private final TiActivityPlugin<WelcomePresenter, WelcomeView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<WelcomePresenter>() {
                @NonNull
                @Override
                public WelcomePresenter providePresenter() {
                    Intent intent = getIntent();
                    boolean isLoginFailed = intent.getBooleanExtra("login_failed", false);
                    mWelcomePresenter = new WelcomePresenter(isLoginFailed);
                    return mWelcomePresenter;
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mWelcomePresenter = mPresenterPlugin.getPresenter();
    }

    private void initViewPager(ViewPager viewPager) {
        WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
        viewPager.setPageTransformer(true, new StackTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mNextPageButton != null) {
                    manageButtonVisibility(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(welcomePagerAdapter);
        mFlexibleIndicator.initViewPager(mViewPager);
    }

    private void manageButtonVisibility(int position) {
        if (position > 1) {
            mNextPageButton.setVisibility(View.GONE);
        } else {
            mNextPageButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_next)
    public void nextPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Override
    public void manageUserStartup() {
        boolean userLoginStatus = App.readFromPreferences(this, "user_login_status", false);
        if (userLoginStatus) {
            startActivity(new Intent(this, SignInActivity.class));
        } else {
            initViewPager(mViewPager);
        }
    }

    @Override
    public void showLoginFailedMessage() {
        mViewPager.setCurrentItem(LOGIN_PAGE);
        Snackbar.make(parentLayout, getString(R.string.error_login_message), Snackbar.LENGTH_LONG).show();
    }


}
