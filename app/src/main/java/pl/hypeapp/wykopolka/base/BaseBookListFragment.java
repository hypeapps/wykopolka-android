package pl.hypeapp.wykopolka.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Wave;

import net.grandcentrix.thirtyinch.TiFragment;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.TiView;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.BooksRecyclerAdapter;
import pl.hypeapp.wykopolka.extra.circlerefreshlayout.CircleRefreshLayout;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.ui.activity.BookActivity;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.view.BookListView;
import rx.functions.Action1;

public class BaseBookListFragment<P extends TiPresenter<V>, V extends TiView> extends TiFragment<P, V>
        implements BookListView, CircleRefreshLayout.OnCircleRefreshListener {
    private ProgressBar mSpinLoading;
    private TextView loadingMessage;
    private View errorView;
    private RecyclerView mRecyclerView;
    private boolean isRefreshing = false;
    private boolean isFirstRun;
    public BooksRecyclerAdapter mRecyclerAdapter;
    public CircleRefreshLayout mCircleRefreshLayout;
    private List<Book> mBooks;

    @NonNull
    @Override
    public P providePresenter() {
        return null;
    }

    protected void initBookListViews(View view) {
        isFirstRun = true;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.book_list);
        mSpinLoading = (ProgressBar) view.findViewById(R.id.spin_loading);
        loadingMessage = (TextView) view.findViewById(R.id.tv_loading);
        errorView = (View) view.findViewById(R.id.error_view);
        mCircleRefreshLayout = (CircleRefreshLayout) view.findViewById(R.id.refresh_layout);
        mCircleRefreshLayout.setOnRefreshListener(this);
    }

    public void initRecyclerAdapter() {
        mRecyclerAdapter = new BooksRecyclerAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mRecyclerAdapter);
        scaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerAdapter.getOnBookClicks().subscribe(new Action1<BooksRecyclerAdapter.BooksRecyclerHolder>() {
            @Override
            public void call(BooksRecyclerAdapter.BooksRecyclerHolder booksRecyclerHolder) {
                showBookActivity(mBooks, booksRecyclerHolder);
            }
        });
        mRecyclerView.setAdapter(scaleInAnimationAdapter);
    }

    @Override
    public void setBookData(List<Book> books) {
        this.mBooks = books;
        mRecyclerAdapter.setData(this.mBooks);
        stopRefreshing();
    }

    @Override
    public void startLoadingAnimation() {
        if (isFirstRun) {
            Wave wave = new Wave();
            mSpinLoading.setIndeterminateDrawable(wave);
        }
    }

    @Override
    public void stopLoadingAnimation() {
        if (isFirstRun) {
            loadingMessage.setVisibility(View.GONE);
            mSpinLoading.setVisibility(View.GONE);
            isFirstRun = false;
        }
    }

    @Override
    public void showError() {
        if (errorView != null) {
            errorView.setVisibility(View.VISIBLE);
        }
        if (mSpinLoading.getVisibility() == View.VISIBLE) {
            mSpinLoading.setVisibility(View.GONE);
            loadingMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideError() {
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void retry() {
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if (mSpinLoading != null) {
            mSpinLoading.setVisibility(View.VISIBLE);
            loadingMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void completeRefresh() {
        isRefreshing = false;
    }

    @Override
    public void refreshing() {
        isRefreshing = true;
    }

    @Override
    public void stopRefreshing() {
        if (isRefreshing) {
            mCircleRefreshLayout.postDelayed(finishRefreshingRun, 1000);
        }
    }

    private Runnable finishRefreshingRun = new Runnable() {
        @Override
        public void run() {
            mCircleRefreshLayout.finishRefreshing();
        }
    };

    protected void showBookActivity(List<Book> mBooks, BooksRecyclerAdapter.BooksRecyclerHolder holder) {
        int position = holder.getLayoutPosition();
        String bookId = mBooks.get(position).getBookId();
        String bookTitle = mBooks.get(position).getTitle();
        String bookCover = mBooks.get(position).getCover();

        Intent intentBookActivity = new Intent(getActivity(), BookActivity.class);
        intentBookActivity.putExtra("BOOK_ID", bookId);
        intentBookActivity.putExtra("BOOK_TITLE", bookTitle);
        intentBookActivity.putExtra("BOOK_COVER", bookCover);

        if (BuildUtil.isMinApi21()) {
            String transitionName = getString(R.string.transition_book_cover);
            Pair<View, String> p1 = Pair.create((View) holder.bookThumbnail, transitionName);
            Pair<View, String> p2 = Pair.create((View) holder.gradient, getString(R.string.transition_gradient));
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), p1, p2);
            startActivity(intentBookActivity, transitionActivityOptions.toBundle());
        } else {
            startActivity(intentBookActivity);
        }
    }

}
