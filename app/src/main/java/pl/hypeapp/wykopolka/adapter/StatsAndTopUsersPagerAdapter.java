package pl.hypeapp.wykopolka.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.hypeapp.wykopolka.ui.fragment.StatisticFragment;
import pl.hypeapp.wykopolka.ui.fragment.TopUsersFragment;

public class StatsAndTopUsersPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 2;

    public StatsAndTopUsersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StatisticFragment();
            case 1:
                return new TopUsersFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
