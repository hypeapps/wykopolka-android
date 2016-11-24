package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.WelcomePagerAdapter;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.viewpager) ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initViewPager(mViewPager);
    }

    private void initViewPager(ViewPager viewPager) {
        WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
//        viewPager.setPageTransformer(true, new ParallaxPageTransformer());
        viewPager.setAdapter(welcomePagerAdapter);
//        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.page_indicator);
//        extensiblePageIndicator.initViewPager(mViewPager);
    }
}
