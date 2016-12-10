package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.SearchBookRecyclerAdapter;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.SearchBookPresenter;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.view.SearchBookView;
import rx.functions.Action1;

public class SearchBookActivity extends CompositeActivity implements SearchBookView, MaterialSearchView.OnQueryTextListener {
    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();
    private SearchBookPresenter mSearchBookPresenter;
    private SearchBookRecyclerAdapter mSearchedByTitleRecyclerAdapter;
    private SearchBookRecyclerAdapter mSearchedByAuthorRecyclerAdapter;
    private SearchBookRecyclerAdapter mSearchedByGenreRecyclerAdapter;
    @BindView(R.id.search_view) MaterialSearchView mSearchView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.book_searched_by_title) RecyclerView mSearchedByTitleRecyclerView;
    @BindView(R.id.book_searched_by_author) RecyclerView mSearchedByAuthorRecyclerView;
    @BindView(R.id.book_searched_by_genre) RecyclerView mSearchedByGenreRecyclerView;
    @BindView(R.id.tv_search_failed) TextView mTvSearchFailed;
    @BindView(R.id.tv_search_by_title) TextView mTvSearchByTitle;
    @BindView(R.id.tv_search_by_author) TextView mTvSearchByAuthor;
    @BindView(R.id.tv_search_by_genre) TextView mTvSearchByGenre;
    @BindView(R.id.error_view) View mErrorView;
    @BindView(R.id.loading_view) View mLoadingView;
    @BindView(R.id.search_results) View mSearchResultsView;

    public SearchBookActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
    }

    private final TiActivityPlugin<SearchBookPresenter, SearchBookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<SearchBookPresenter>() {
                @NonNull
                @Override
                public SearchBookPresenter providePresenter() {
                    mSearchBookPresenter = new SearchBookPresenter();
                    return new SearchBookPresenter();
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        ButterKnife.bind(this);
        mToolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(mToolbar);
        mSearchBookPresenter = mPresenterPlugin.getPresenter();
        createAdapters();
        onIntentQuery();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_book, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchBookPresenter.searchByQuery(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setSearchedBooksByTitle(List<Book> books) {
        mSearchedByTitleRecyclerAdapter.setData(books);
        if (mTvSearchByTitle != null && books.size() != 0) {
            mTvSearchByTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSearchedBooksByAuthor(List<Book> books) {
        mSearchedByAuthorRecyclerAdapter.setData(books);
        if (mTvSearchByAuthor != null && books.size() != 0) {
            mTvSearchByAuthor.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSearchedBooksByGenre(List<Book> books) {
        mSearchedByGenreRecyclerAdapter.setData(books);
        if (mTvSearchByGenre != null && books.size() != 0) {
            mTvSearchByGenre.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void resetResults() {
        if (mSearchResultsView != null) {
            mTvSearchByGenre.setVisibility(View.GONE);
            mTvSearchByAuthor.setVisibility(View.GONE);
            mTvSearchByTitle.setVisibility(View.GONE);
        }
        mSearchedByTitleRecyclerAdapter.setData(Collections.<Book>emptyList());
        mSearchedByAuthorRecyclerAdapter.setData(Collections.<Book>emptyList());
        mSearchedByGenreRecyclerAdapter.setData(Collections.<Book>emptyList());
    }

    @Override
    public void showSearchFailed() {
        if (mTvSearchFailed != null) {
            mTvSearchFailed.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideSearchFailed() {
        if (mTvSearchFailed != null) {
            mTvSearchFailed.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick(R.id.btn_error_retry)
    public void retry() {
        mSearchBookPresenter.retry();
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

    private void createAdapters() {
        mSearchedByTitleRecyclerAdapter = new SearchBookRecyclerAdapter(this);
        initRecyclerAdapter(mSearchedByTitleRecyclerAdapter, mSearchedByTitleRecyclerView);
        mSearchedByAuthorRecyclerAdapter = new SearchBookRecyclerAdapter(this);
        initRecyclerAdapter(mSearchedByAuthorRecyclerAdapter, mSearchedByAuthorRecyclerView);
        mSearchedByGenreRecyclerAdapter = new SearchBookRecyclerAdapter(this);
        initRecyclerAdapter(mSearchedByGenreRecyclerAdapter, mSearchedByGenreRecyclerView);
    }

    private void initRecyclerAdapter(SearchBookRecyclerAdapter recyclerAdapter, RecyclerView recyclerView) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(recyclerAdapter);
        scaleInAnimationAdapter.setFirstOnly(true);
        recyclerAdapter.getOnBookClicks().subscribe(new Action1<Pair<SearchBookRecyclerAdapter.SearchBookRecyclerHolder, Book>>() {
            @Override
            public void call(Pair<SearchBookRecyclerAdapter.SearchBookRecyclerHolder, Book> searchBookRecyclerHolderBookPair) {
                startBookActivity(searchBookRecyclerHolderBookPair);
            }
        });
        recyclerView.setAdapter(scaleInAnimationAdapter);
    }

    private void onIntentQuery() {
        String query = getIntent().getStringExtra("SEARCH_QUERY");
        if (query != null) {
            mSearchBookPresenter.searchByQueryOnIntent(query);
        }
    }

    private void startBookActivity(Pair<SearchBookRecyclerAdapter.SearchBookRecyclerHolder, Book> clickedBook) {
        Book book = clickedBook.second;
        SearchBookRecyclerAdapter.SearchBookRecyclerHolder holder = clickedBook.first;
        Intent intentBookActivity = new Intent(this, BookActivity.class);
        intentBookActivity.putExtra("book", book);

        if (BuildUtil.isMinApi21()) {
            String transitionName = getString(R.string.transition_book_cover);
            Pair<View, String> p1 = Pair.create((View) holder.bookCover, transitionName);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, p1);
            startActivity(intentBookActivity, transitionActivityOptions.toBundle());
        } else {
            startActivity(intentBookActivity);
        }
    }
}