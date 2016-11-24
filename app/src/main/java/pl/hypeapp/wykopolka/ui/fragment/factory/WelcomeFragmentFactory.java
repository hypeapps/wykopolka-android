package pl.hypeapp.wykopolka.ui.fragment.factory;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.hypeapp.wykopolka.R;

public class WelcomeFragmentFactory extends Fragment {

    public static WelcomeFragmentFactory newInstance(int position) {
        Bundle args = new Bundle();
        WelcomeFragmentFactory fragment = new WelcomeFragmentFactory();
        args.putInt("fragment_faq_position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutResource = getProperIdLayout(getArguments().getInt("fragment_faq_position", 0));
        View v = inflater.inflate(layoutResource, container, false);
        return v;
    }

    private int getProperIdLayout(int position) {
        switch (position) {
            case 0:
                return R.layout.fragment_welcome_about;
            case 1:
                return R.layout.fragment_welcome_how_it_works;
            case 2:
                return R.layout.activity_dashboard;
//            case 3:
//                return R.layout.htu_fragment4;
        }
        return 0;
    }

}