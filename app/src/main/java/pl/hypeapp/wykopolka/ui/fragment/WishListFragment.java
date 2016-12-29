package pl.hypeapp.wykopolka.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Wave;

import net.grandcentrix.thirtyinch.TiFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.WishListRecyclerAdapter;
import pl.hypeapp.wykopolka.extra.circlerefreshlayout.CircleRefreshLayout;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.presenter.WishListPresenter;
import pl.hypeapp.wykopolka.ui.ConfirmClaimedBookDialog;
import pl.hypeapp.wykopolka.ui.activity.BookActivity;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.view.WishListView;
import rx.functions.Action1;

public class WishListFragment extends TiFragment<WishListPresenter, WishListView> implements WishListView, CircleRefreshLayout.OnCircleRefreshListener {
    private WishListPresenter mWishListPresenter;
    private boolean isRefreshing = false;
    private List<Book> mBooks;
    private ConfirmClaimedBookDialog mDialog;
    private WishListRecyclerAdapter mRecyclerAdapter;
    private View parentLayout;
    @BindView(R.id.spin_loading) ProgressBar mSpinLoading;
    @BindView(R.id.loading_view) View mLoadingView;
    @BindView(R.id.error_view) View errorView;
    @BindView(R.id.book_list) RecyclerView mRecyclerView;
    @BindView(R.id.tv_book_list_null) TextView mEmptyBookListMessage;
    @BindView(R.id.refresh_layout) CircleRefreshLayout mCircleRefreshLayout;
    FloatingActionButton mFab;

    public WishListFragment() {
    }

    @NonNull
    @Override
    public WishListPresenter providePresenter() {
        String accountKey = App.readFromPreferences(getContext(), "user_account_key", null);
        mWishListPresenter = new WishListPresenter(accountKey);
        return mWishListPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.fragment_wish_list, container, false);
        ButterKnife.bind(this, parentLayout);
        initViews();
        initRecyclerAdapter();
        return parentLayout;
    }

    private void initViews() {
        mCircleRefreshLayout.setOnRefreshListener(this);
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_book);
        mDialog = new ConfirmClaimedBookDialog(getActivity());
    }

    public void initRecyclerAdapter() {
        mRecyclerAdapter = new WishListRecyclerAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mRecyclerAdapter);
        scaleInAnimationAdapter.setFirstOnly(true);
        mRecyclerAdapter.getOnBookClick()
                .subscribe(new Action1<WishListRecyclerAdapter.WishListRecyclerHolder>() {
                    @Override
                    public void call(WishListRecyclerAdapter.WishListRecyclerHolder wishListRecyclerHolder) {
                        startBookActivity(wishListRecyclerHolder);
                    }
                });
        mRecyclerAdapter.getOnConfirmClick()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer position) {
                        mWishListPresenter.onConfirmBook(position);
                    }
                });
        mRecyclerView.addOnScrollListener(onScrollListener);
        mRecyclerView.setAdapter(scaleInAnimationAdapter);
    }

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                showFab();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0)
                hideFab();
            else if (dy < 0)
                showFab();
        }
    };

    @Override
    public void setBookData(List<Book> books) {
        this.mBooks = books;
        initRecyclerAdapter();
        mRecyclerAdapter.setData(mBooks);
        if (mBooks.size() == 0) {
            mEmptyBookListMessage.setVisibility(View.VISIBLE);
        } else {
            mEmptyBookListMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void showConfirmDialog(final Book book) {
        mDialog.setBookData(book);
        mDialog.getConfirmButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quality = mDialog.getQuality();
                mWishListPresenter.onBookClaimConfirmed(book, quality);
            }
        });
        mDialog.show();
    }

    @Override
    public void dismissConfirmDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void setDialogInLoading() {
        mDialog.getConfirmButton().setEnabled(false);
        mDialog.setCancelable(false);
        mDialog.startLoading();
    }

    @Override
    public void dismissDialogLoading() {
        mDialog.setCancelable(true);
        mDialog.stopLoading();
        mDialog.getConfirmButton().setEnabled(true);
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
    public void showConfirmationError() {
        Snackbar.make(parentLayout, R.string.error_message_default, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        if (errorView != null) {
            errorView.setVisibility(View.VISIBLE);
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
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick(R.id.btn_error_retry)
    public void retry() {
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        mWishListPresenter.initRefreshData();
    }

    @Override
    public void completeRefresh() {
        isRefreshing = false;
    }

    @Override
    public void refreshing() {
        isRefreshing = true;
        mWishListPresenter.initRefreshData();
    }

    @Override
    public void stopRefreshing() {
        if (isRefreshing) {
            mCircleRefreshLayout.postDelayed(finishRefreshingRun, 1000);
        }
    }

    private void showFab() {
        if (!mFab.isShown()) {
            mFab.show();
        }
    }

    private void hideFab() {
        if (mFab.isShown()) {
            mFab.hide();
        }
    }

    private Runnable finishRefreshingRun = new Runnable() {
        @Override
        public void run() {
            mCircleRefreshLayout.finishRefreshing();
        }
    };

    public void startBookActivity(WishListRecyclerAdapter.WishListRecyclerHolder holder) {
        int position = holder.getLayoutPosition();
        Intent intentBookActivity = new Intent(getActivity(), BookActivity.class);
        intentBookActivity.putExtra("book", mBooks.get(position));

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
