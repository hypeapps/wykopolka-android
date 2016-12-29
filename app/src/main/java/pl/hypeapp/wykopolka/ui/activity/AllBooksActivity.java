package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Wave;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.AllBooksRecyclerAdapter;
import pl.hypeapp.wykopolka.extra.circlerefreshlayout.CircleRefreshLayout;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.CrashlyticsPlugin;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.AllBooksPresenter;
import pl.hypeapp.wykopolka.ui.listener.EndlessRecyclerViewScrollListener;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.view.AllBooksView;
import rx.functions.Action1;

public class AllBooksActivity extends CompositeActivity implements AllBooksView, CircleRefreshLayout.OnCircleRefreshListener {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.all_books_list) RecyclerView mRecyclerView;
    @BindView(R.id.spin_loading) ProgressBar mSpinLoading;
    @BindView(R.id.loading_view) View mLoadingView;
    @BindView(R.id.tv_book_list_null) TextView mEmptyBookListMessage;
    @BindView(R.id.error_view) View mErrorView;
    @BindView(R.id.refresh_layout) CircleRefreshLayout mCircleRefreshLayout;
    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();
    private final CrashlyticsPlugin mCrashlyticsPlugin = new CrashlyticsPlugin();
    private boolean isRefreshing = false;
    private AllBooksRecyclerAdapter mRecyclerAdapter;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private List<Book> mBooks;
    private AllBooksPresenter mAllBooksPresenter;

    public AllBooksActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
        addPlugin(mCrashlyticsPlugin);
    }


    private final TiActivityPlugin<AllBooksPresenter, AllBooksView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<AllBooksPresenter>() {
                @NonNull
                @Override
                public AllBooksPresenter providePresenter() {
                    mAllBooksPresenter = new AllBooksPresenter();
                    return mAllBooksPresenter;
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);
        ButterKnife.bind(this);
        mToolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(mToolbar);
        mAllBooksPresenter = mPresenterPlugin.getPresenter();
        mCircleRefreshLayout.setOnRefreshListener(this);
        initRecyclerAdapter();
    }

    private void initRecyclerAdapter() {
        mRecyclerAdapter = new AllBooksRecyclerAdapter(this);
        int orientation = getResources().getConfiguration().orientation;
        GridLayoutManager gridLayoutManager;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = new GridLayoutManager(this, 5);
        } else {
            gridLayoutManager = new GridLayoutManager(this, 4);
        }
        mRecyclerView.setLayoutManager(gridLayoutManager);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mRecyclerAdapter);
        alphaInAnimationAdapter.setFirstOnly(true);
        mRecyclerAdapter.getOnBookClicks()
                .subscribe(new Action1<AllBooksRecyclerAdapter.AllBooksRecyclerHolder>() {
                    @Override
                    public void call(AllBooksRecyclerAdapter.AllBooksRecyclerHolder allBooksRecyclerHolder) {
                        startBookActivity(mBooks, allBooksRecyclerHolder);
                    }
                });
        mRecyclerView.addOnScrollListener(initEndlessScrollListener(gridLayoutManager));
        mRecyclerView.setAdapter(alphaInAnimationAdapter);
    }

    private EndlessRecyclerViewScrollListener initEndlessScrollListener(GridLayoutManager gridLayoutManager) {
        mScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mAllBooksPresenter.loadMoreBooks(++page, totalItemsCount);
            }
        };
        return mScrollListener;
    }

    @Override
    public void onLoadedMoreDataFromApi(List<Book> books, final int itemsCount) {
        mRecyclerAdapter.setMoreData(books);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerAdapter.notifyItemRangeInserted(itemsCount, 15);
            }
        });
        this.mBooks.addAll(books);
    }

    @Override
    public void setBookData(List<Book> books) {
        if (books != null) {
            this.mBooks = books;
            initRecyclerAdapter();
            mRecyclerAdapter.setData(this.mBooks);
            mEmptyBookListMessage.setVisibility(View.GONE);
        } else {
            mEmptyBookListMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void startLoadingAnimation() {
        Wave wave = new Wave();
        mSpinLoading.setIndeterminateDrawable(wave);
    }

    @Override
    public void stopLoadingAnimation() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.VISIBLE);
        }
        if (mLoadingView.getVisibility() == View.VISIBLE) {
            mLoadingView.setVisibility(View.GONE);
        }
        if (mEmptyBookListMessage != null) {
            mEmptyBookListMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideError() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick(R.id.btn_error_retry)
    public void retry() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        mAllBooksPresenter.initRefreshData();
    }

    @Override
    public void stopRefreshing() {
        if (isRefreshing) {
            mCircleRefreshLayout.postDelayed(finishRefreshingRun, 1000);
        }
    }

    @Override
    public void completeRefresh() {
        isRefreshing = false;
    }

    @Override
    public void refreshing() {
        isRefreshing = true;
        mAllBooksPresenter.initRefreshData();
    }

    private Runnable finishRefreshingRun = new Runnable() {
        @Override
        public void run() {
            mCircleRefreshLayout.finishRefreshing();
        }
    };

    private void startBookActivity(List<Book> books, AllBooksRecyclerAdapter.AllBooksRecyclerHolder holder) {
        int position = holder.getLayoutPosition();
        Intent intentBookActivity = new Intent(AllBooksActivity.this, BookActivity.class);
        intentBookActivity.putExtra("book", books.get(position));

        if (BuildUtil.isMinApi21()) {
            String transitionName = getString(R.string.transition_book_cover);
            Pair<View, String> p1 = Pair.create((View) holder.bookCover, transitionName);
//            Pair<View, String> p2 = Pair.create((View) holder.gradient, getString(R.string.transition_gradient));
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, p1);
            startActivity(intentBookActivity, transitionActivityOptions.toBundle());
        } else {
            startActivity(intentBookActivity);
        }
    }
}