package pl.hypeapp.wykopolka.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.hypeapp.wykopolka.ui.fragment.LoginViaWykopFragment;
import pl.hypeapp.wykopolka.ui.fragment.factory.WelcomeFragmentFactory;

public class WelcomePagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;
    private static final int NUM_LOGIN_VIA_WYKOP_PAGE = 2;

    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (isLoginViaWykopPosition(position)) {
            return new LoginViaWykopFragment();
        }
        return WelcomeFragmentFactory.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    private boolean isLoginViaWykopPosition(int position) {
        return position == NUM_LOGIN_VIA_WYKOP_PAGE;
    }
}
