package pl.hypeapp.wykopolka.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.hypeapp.wykopolka.ui.fragment.AddedBooksFragment;
import pl.hypeapp.wykopolka.ui.fragment.MyBooksFragment;

public class BookPanelPagerAdapter extends FragmentPagerAdapter {

    public BookPanelPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MyBooksFragment();
            case 1:
                return new AddedBooksFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
