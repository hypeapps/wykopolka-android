package pl.hypeapp.wykopolka.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.hypeapp.wykopolka.ui.fragment.welcome.AboutFragment;
import pl.hypeapp.wykopolka.ui.fragment.welcome.HowItWorksFragment;
import pl.hypeapp.wykopolka.ui.fragment.welcome.LoginViaWykopFragment;

public class WelcomePagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AboutFragment();
            case 1:
                return new HowItWorksFragment();
            case 2:
                return new LoginViaWykopFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
