package pl.hypeapp.wykopolka.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.presenter.TopUsersPresenter;
import pl.hypeapp.wykopolka.view.TopUsersView;

public class TopUsersFragment extends TiFragment<TopUsersPresenter, TopUsersView> implements TopUsersView {

    public TopUsersFragment() {
    }

    @NonNull
    @Override
    public TopUsersPresenter providePresenter() {
        return new TopUsersPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_users, container, false);
        return view;
    }
}
