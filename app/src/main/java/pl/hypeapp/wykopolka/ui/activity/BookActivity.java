package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.presenter.BookPresenter;
import pl.hypeapp.wykopolka.ui.listener.AppBarStateChangeListener;
import pl.hypeapp.wykopolka.view.BookView;

public class BookActivity extends CompositeActivity implements BookView {

    private final TiActivityPlugin<BookPresenter, BookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<BookPresenter>() {
                @NonNull
                @Override
                public BookPresenter providePresenter() {
                    return new BookPresenter();
                }
            });

    public BookActivity() {
        addPlugin(mPresenterPlugin);
    }

    private static final String EMPTY_STRING = "";
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    private String mBookTitle;
    private boolean isSearchViewShown = false;
    private AppBarStateChangeListener.State mState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        mToolbar = initToolbar();

        mBookTitle = "Awantury na tle powszechnego ciążenia";
        mCollapsingToolbarLayout.setTitle(mBookTitle);
//        toolbarTextAppernce();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_book, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                if (mState == AppBarStateChangeListener.State.IDLE || mState == AppBarStateChangeListener.State.EXPANDED) {
                    mCollapsingToolbarLayout.setTitle(mBookTitle);
                } else if (mState == AppBarStateChangeListener.State.COLLAPSED) {
                    mCollapsingToolbarLayout.setTitle(EMPTY_STRING);
                }
                isSearchViewShown = true;
            }

            @Override
            public void onSearchViewClosed() {
                mCollapsingToolbarLayout.setTitle(mBookTitle);
                isSearchViewShown = false;
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                mState = state;
                if (state == State.EXPANDED || state == State.IDLE) {
                    mCollapsingToolbarLayout.setTitle(mBookTitle);
                } else if (state == State.COLLAPSED && isSearchViewShown) {
                    mCollapsingToolbarLayout.setTitle(EMPTY_STRING);
                }
            }
        });

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
    public void setBookInfo(Book book) {

    }

    @Override
    public void setBookOwner(String addedBy, String ownedBy) {

    }

    @Override
    public void loadPdfBookCard() {

    }

    @Override
    public void editBookDescription() {

    }

    @Override
    public void addBookToWishlist() {

    }

    private Toolbar initToolbar() {
        mToolbar.setTitle(EMPTY_STRING);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;
    }

    private void toolbarTextAppernce() {
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
    }
}

