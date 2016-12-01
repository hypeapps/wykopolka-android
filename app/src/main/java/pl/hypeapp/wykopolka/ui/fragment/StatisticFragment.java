package pl.hypeapp.wykopolka.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.presenter.StatisticPresenter;
import pl.hypeapp.wykopolka.view.StatisticView;

public class StatisticFragment extends TiFragment<StatisticPresenter, StatisticView> implements StatisticView {

    public StatisticFragment() {
    }

    @NonNull
    @Override
    public StatisticPresenter providePresenter() {
        return new StatisticPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        return view;
    }
}
