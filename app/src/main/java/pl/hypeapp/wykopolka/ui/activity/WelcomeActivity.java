package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.WelcomePagerAdapter;
import pl.hypeapp.wykopolka.extra.pagetransformer.StackTransformer;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.flexibleIndicator) ExtensiblePageIndicator mFlexibleIndicator;
    @BindView(R.id.iv_next) ImageView mNextPageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initViewPager(mViewPager);
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
}
