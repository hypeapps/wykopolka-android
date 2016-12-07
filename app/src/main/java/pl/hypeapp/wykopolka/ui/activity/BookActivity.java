package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.BookPresenter;
import pl.hypeapp.wykopolka.ui.listener.AppBarStateChangeListener;
import pl.hypeapp.wykopolka.view.BookView;
import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class BookActivity extends CompositeActivity implements BookView {
    private static final String WYKOPOLKA_IMG_HOST = App.WYKOPOLKA_IMG_HOST;
    private boolean isSearchViewShown = false;
    private AppBarStateChangeListener.State mState;
    private SmallBang mSmallBang;
    private Book mBook;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.app_bar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.search_view) MaterialSearchView mSearchView;
    @BindView(R.id.fab_add_to_wishlist) FloatingActionButton mFabButton;
    @BindView(R.id.search_status_bar) View searchStatusBar;
    @BindView(R.id.iv_book_cover) ImageView mBookCover;
    @BindView(R.id.cv_overview) CardView mOverviewCard;
    @BindViews({R.id.tv_author, R.id.tv_description, R.id.tv_genre}) List<TextView> mBookDescriptionsTextViews;
    @BindViews({R.id.tv_book_added_by, R.id.tv_book_owned_by}) List<TextView> mBookHoldersTextViews;

    public BookActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
    }

    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();

    private final TiActivityPlugin<BookPresenter, BookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<BookPresenter>() {
                @NonNull
                @Override
                public BookPresenter providePresenter() {
                    String accountKey = App.readFromPreferences(BookActivity.this, "user_account_key", null);
                    ;
                    return new BookPresenter(accountKey, getBookIntentExtra());
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        mSmallBang = SmallBang.attach2Window(this);
        mToolbar = mToolbarPlugin.initToolbarWithEmptyTitle(mToolbar);
        mBook = getBookIntentExtra();
        mCollapsingToolbarLayout.setTitle(mBook.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final String EMPTY_STRING = "";
        getMenuInflater().inflate(R.menu.menu_single_book, menu);

        searchStatusBar.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        MenuItem item = menu.findItem(R.id.action_search);

        mSearchView.setMenuItem(item);
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchStatusBar.setVisibility(View.VISIBLE);
                if (mState == AppBarStateChangeListener.State.IDLE || mState == AppBarStateChangeListener.State.EXPANDED) {
                    mCollapsingToolbarLayout.setTitle(mBook.getTitle());
                } else if (mState == AppBarStateChangeListener.State.COLLAPSED) {
                    mCollapsingToolbarLayout.setTitle(EMPTY_STRING);
                }
                isSearchViewShown = true;
            }

            @Override
            public void onSearchViewClosed() {
                searchStatusBar.setVisibility(View.GONE);
                mCollapsingToolbarLayout.setTitle(mBook.getTitle());
                isSearchViewShown = false;
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                mState = state;
                if (state == State.EXPANDED || state == State.IDLE) {
                    mCollapsingToolbarLayout.setTitle(mBook.getTitle());
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
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            removeGradient();
            super.onBackPressed();
        }
    }

    @Override
    public void setBookDescription(Book book) {
        mBookDescriptionsTextViews.get(0).setText(book.getAuthor());
        mBookDescriptionsTextViews.get(1).setText(book.getDesc());
        mBookDescriptionsTextViews.get(2).setText(book.getGenre());
    }

    @Override
    public void setBookCover(String coverUrl) {
        if (mBookCover.getDrawable() == null) {
            Glide.with(this)
                    .load(WYKOPOLKA_IMG_HOST + coverUrl)
                    .override(300, 400)
                    .skipMemoryCache(true)
                    .into(mBookCover);
        }
    }

    @Override
    public void setWishStatusWilled() {
        mFabButton.setImageResource(R.drawable.ic_favorite_white_36dp);
    }

    @Override
    public void setWishStatusNotWilled() {
        mFabButton.setImageResource(R.drawable.ic_favorite_border_white_36dp);
    }

    @Override
    public void setWishIconDisabled() {
        mFabButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.wishFabButtonDisabledColor)));
        mFabButton.setImageResource(R.drawable.ic_favorite_white_disabled_36dp);
        mFabButton.setClickable(false);
    }

    @Override
    public void setBookHolders(String addedBy, String ownedBy) {
        mBookHoldersTextViews.get(0).setText(addedBy);
        mBookHoldersTextViews.get(1).setText(ownedBy);
    }


    @Override
    @OnClick(R.id.btn_edit_book)
    public void editBookDescription() {

    }

    @Override
    @OnClick(R.id.btn_load_pdf)
    public void loadPdfBookCard() {

    }

    @Override
    @OnClick(R.id.fab_add_to_wishlist)
    public void addBookToWishlist() {
        mSmallBang.bang(mFabButton, new SmallBangListener() {
            @Override
            public void onAnimationStart() {
                mFabButton.setImageResource(R.drawable.ic_favorite_white_36dp);
            }

            @Override
            public void onAnimationEnd() {

            }
        });
        showSnackbarBookWishlistedSuccesful();
    }

    @Override
    public void animateCardEnter() {
        if (mOverviewCard != null) {
            YoYo.with(Techniques.SlideInLeft)
                    .duration(800)
                    .playOn(mOverviewCard);
        }
    }

    @Override
    public void animateFabButtonEnter() {
        if (mFabButton != null) {
            mFabButton.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.ZoomIn)
                    .duration(1000)
                    .playOn(mFabButton);
        }
    }

    @Override
    public void showSnackbarBookWishlistedSuccesful() {
        Snackbar snackbar = Snackbar.make(mFabButton, R.string.message_added_to_wishlist, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.action_go_to_wishlist, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent to wish list
            }
        }).show();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private Book getBookIntentExtra() {
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        return book;
    }

    private void removeGradient() {
        View gradient = findViewById(R.id.gradient);
        if (gradient != null) gradient.setVisibility(View.GONE);
    }
}

