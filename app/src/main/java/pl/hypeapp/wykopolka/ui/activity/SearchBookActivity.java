package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.BooksRecyclerAdapter;
import pl.hypeapp.wykopolka.adapter.SearchBookRecyclerAdapter;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.SearchBookPresenter;
import pl.hypeapp.wykopolka.view.SearchBookView;
import rx.functions.Action1;

public class SearchBookActivity extends CompositeActivity implements SearchBookView {
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
        createAdapters();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_book, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Log.e("MENU", " SEARCH ACTION");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    }

    @Override
    public void setSearchedBooksByAuthor(List<Book> books) {
        mSearchedByAuthorRecyclerAdapter.setData(books);
    }

    @Override
    public void setSearchedBooksByGenre(List<Book> books) {
        mSearchedByGenreRecyclerAdapter.setData(books);
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
        recyclerAdapter.getOnBookClicks().subscribe(new Action1<BooksRecyclerAdapter.BooksRecyclerHolder>() {
            @Override
            public void call(BooksRecyclerAdapter.BooksRecyclerHolder booksRecyclerHolder) {
//                startBookActivity(mBooks, booksRecyclerHolder);
            }
        });
        recyclerView.setAdapter(scaleInAnimationAdapter);
    }
}