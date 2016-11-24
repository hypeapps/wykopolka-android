package pl.hypeapp.wykopolka.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.BooksRecyclerAdapter;
import pl.hypeapp.wykopolka.extra.circlerefreshlayout.CircleRefreshLayout;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;
import pl.hypeapp.wykopolka.ui.activity.BookActivity;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.view.AddedBooksView;

public class AddedBooksFragment extends TiFragment<AddedBooksPresenter, AddedBooksView> implements AddedBooksView {
    private BooksRecyclerAdapter mRecyclerAdapter;
    private AddedBooksPresenter mAddedBooksPresenter;
    private List<Book> books;
    private boolean isRefreshing = false;
    @BindView(R.id.refresh_layout)
    CircleRefreshLayout mCircleRefreshLayout;
    @BindView(R.id.book_list)
    RecyclerView mRecyclerView;

    public AddedBooksFragment() {
    }

    @NonNull
    @Override
    public AddedBooksPresenter providePresenter() {
        String accountKey = App.readFromPreferences(getContext(), "user_account_key", null);
        mAddedBooksPresenter = new AddedBooksPresenter(accountKey);
        return mAddedBooksPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_added_books, container, false);
        ButterKnife.bind(this, view);
        initRecyclerAdapter();
        mCircleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {
                Log.e("complete", "ref");
                isRefreshing = false;
            }

            @Override
            public void refreshing() {
                Log.e("refreshing", "ref");
                isRefreshing = true;
                mAddedBooksPresenter.initRefreshData();
            }
        });
        return view;
    }

    @Override
    public void setBookData(List<Book> books) {
        this.books = books;
        mRecyclerAdapter.setData(this.books);
        if (isRefreshing) {
            mCircleRefreshLayout.postDelayed(finishRefreshing, 1000);
        }
    }

    private Runnable finishRefreshing = new Runnable() {
        @Override
        public void run() {
            mCircleRefreshLayout.finishRefreshing();
        }
    };

    @Override
    public void onRefreshCompleted() {

    }

    private void initRecyclerAdapter() {
        mRecyclerAdapter = new BooksRecyclerAdapter(getContext(), new onBookClickListener());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mRecyclerAdapter);
        scaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerView.setAdapter(scaleInAnimationAdapter);
    }

    public class onBookClickListener {

        public void showBookActivity(int position, View sharedCover, View sharedGradient) {
            String bookId = books.get(position).getBookId();
            String bookTitle = books.get(position).getTitle();
            String bookCover = books.get(position).getCover();

            Intent intentBookActivity = new Intent(getActivity(), BookActivity.class);
            intentBookActivity.putExtra("BOOK_ID", bookId);
            intentBookActivity.putExtra("BOOK_TITLE", bookTitle);
            intentBookActivity.putExtra("BOOK_COVER", bookCover);

            if (BuildUtil.isMinApi21()) {
                String transitionName = getString(R.string.transition_book_cover);
                Pair<View, String> p1 = Pair.create((View) sharedCover, transitionName);
                Pair<View, String> p2 = Pair.create((View) sharedGradient, getString(R.string.transition_gradient));
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), p1, p2);
                startActivity(intentBookActivity, transitionActivityOptions.toBundle());
            } else {
                startActivity(intentBookActivity);
            }
        }
    }
}
