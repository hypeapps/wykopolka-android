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
import pl.hypeapp.wykopolka.presenter.SelectedBooksPresenter;
import pl.hypeapp.wykopolka.view.BookListView;

public class SelectedBooksFragment extends BaseBookListFragment<SelectedBooksPresenter, BookListView> {
    private SelectedBooksPresenter mSelectedBooksPresenter;

    public SelectedBooksFragment() {
    }

    @NonNull
    @Override
    public SelectedBooksPresenter providePresenter() {
        String accountKey = App.readFromPreferences(getContext(), "user_account_key", null);
        mSelectedBooksPresenter = new SelectedBooksPresenter(accountKey);
        return mSelectedBooksPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_books, container, false);
        ButterKnife.bind(this, view);
        initBookListViews(view);
        initRecyclerAdapter();
        return view;
    }

    @Override
    public void refreshing() {
        super.refreshing();
        mSelectedBooksPresenter.initRefreshData();
    }

    @Override
    @OnClick(R.id.btn_error_retry)
    public void retry() {
        super.retry();
        mSelectedBooksPresenter.initRefreshData();
    }
}
