package pl.hypeapp.wykopolka.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.presenter.MyBooksPresenter;
import pl.hypeapp.wykopolka.view.MyBooksView;

public class MyBooksFragment extends TiFragment<MyBooksPresenter, MyBooksView> implements MyBooksView {

    public MyBooksFragment() {
    }

    @NonNull
    @Override
    public MyBooksPresenter providePresenter() {
        return new MyBooksPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_books, container, false);
        return view;
    }
}
