package pl.hypeapp.wykopolka.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Wave;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Statistics;
import pl.hypeapp.wykopolka.presenter.StatisticPresenter;
import pl.hypeapp.wykopolka.view.StatisticView;

public class StatisticFragment extends TiFragment<StatisticPresenter, StatisticView> implements StatisticView {
    private StatisticPresenter mStatisticPresenter;
    @BindView(R.id.tv_stats_books_count) TextView mStatisticBookCount;
    @BindView(R.id.tv_stats_active_users) TextView mStatisticActiveUsers;
    @BindView(R.id.tv_stats_changed_own) TextView mStatisticChangedOwn;
    @BindView(R.id.tv_stats_wish_list) TextView mStatisticWishlist;
    @BindView(R.id.tv_stats_average_readed_books) TextView mStatisticAverageReadedBooks;
    @BindView(R.id.spin_loading) ProgressBar mSpinLoading;
    @BindView(R.id.stats_layout) View mStatsLayout;
    @BindView(R.id.error_view) View mErrorView;
    @BindView(R.id.loading_view) View mLoadingView;

    public StatisticFragment() {
    }

    @NonNull
    @Override
    public StatisticPresenter providePresenter() {
        String accountKey = App.readFromPreferences(getContext(), "user_account_key", null);
        mStatisticPresenter = new StatisticPresenter(accountKey);
        return mStatisticPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showStatistics(Statistics statistics) {
        if (mStatsLayout != null) {
            String bookCount = statistics.getStatsCountAllBooks().toString();
            String activeUsers = statistics.getStatsCountAllUsers().toString();
            String changedOwn = statistics.getStatsCountAllTransfers().toString();
            String wishlistedBookCount = statistics.getStatsCountAllWishlistedBooks().toString();
            String averageReadedBooks = statistics.getStatsGetAverageBookPerUser();
            mStatisticBookCount.setText(bookCount);
            mStatisticActiveUsers.setText(activeUsers);
            mStatisticChangedOwn.setText(changedOwn);
            mStatisticWishlist.setText(wishlistedBookCount);
            mStatisticAverageReadedBooks.setText(averageReadedBooks);
            mStatsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideStatistics() {
        if (mStatsLayout != null) {
            mStatsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void startLoadingAnimation() {
        if (mLoadingView != null) {
            Wave wave = new Wave();
            mSpinLoading.setIndeterminateDrawable(wave);
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.VISIBLE);
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
        mStatisticPresenter.loadAgain();
    }
}
