package pl.hypeapp.wykopolka.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.base.BaseBookListFragment;
import pl.hypeapp.wykopolka.base.BaseBookListView;
import pl.hypeapp.wykopolka.presenter.MyBooksPresenter;

public class MyBooksFragment extends BaseBookListFragment<MyBooksPresenter, BaseBookListView> {
    private MyBooksPresenter mMyBooksPresenter;

    public MyBooksFragment() {
    }

    @NonNull
    @Override
    public MyBooksPresenter providePresenter() {
        String accountKey = App.readFromPreferences(getContext(), "user_account_key", null);
        mMyBooksPresenter = new MyBooksPresenter(accountKey);
        return mMyBooksPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_books, container, false);
        ButterKnife.bind(this, view);
        initBookListViews(view);
        initRecyclerAdapter();
        return view;
    }

    @Override
    @OnClick(R.id.btn_error_retry)
    public void retry() {
        super.retry();
        mMyBooksPresenter.initRefreshData();
    }

    @Override
    public void refreshing() {
        super.refreshing();
        mMyBooksPresenter.initRefreshData();
    }

}
