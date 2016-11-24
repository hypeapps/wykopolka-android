package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.WelcomePagerAdapter;
import pl.hypeapp.wykopolka.extra.pagetransformer.StackTransformer;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.flexibleIndicator) ExtensiblePageIndicator mFlexibleIndicator;

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
        viewPager.setAdapter(welcomePagerAdapter);
        mFlexibleIndicator.initViewPager(mViewPager);
    }
}
