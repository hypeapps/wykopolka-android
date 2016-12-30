package pl.hypeapp.wykopolka.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.hypeapp.wykopolka.ui.fragment.DemandQueueFragment;
import pl.hypeapp.wykopolka.ui.fragment.SelectedBooksFragment;
import pl.hypeapp.wykopolka.ui.fragment.WishListFragment;

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public DashboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SelectedBooksFragment();
            case 1:
                return new DemandQueueFragment();
            case 2:
                return new WishListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
