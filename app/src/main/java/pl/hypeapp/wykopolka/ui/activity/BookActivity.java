package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
import pl.hypeapp.wykopolka.plugin.AnalyticsPlugin;
import pl.hypeapp.wykopolka.plugin.CrashlyticsPlugin;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.BookPresenter;
import pl.hypeapp.wykopolka.ui.CardBookDialog;
import pl.hypeapp.wykopolka.ui.listener.AppBarStateChangeListener;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.view.BookView;
import xyz.hanks.library.SmallBang;

public class BookActivity extends CompositeActivity implements BookView, MaterialSearchView.OnQueryTextListener {
    private static final String WYKOPOLKA_IMG_HOST = App.WYKOPOLKA_IMG_HOST;
    private static final int WISH_LIST_PAGE = 2;
    private boolean isSearchViewShown = false;
    private boolean isAddedBook;
    private BookPresenter mBookPresenter;
    private AppBarStateChangeListener.State mState;
    private SmallBang mSmallBang;
    private Book mBook;
    private CardBookDialog mDialog;
    private final AnalyticsPlugin mAnalyticsPlugin = new AnalyticsPlugin();
    private final CrashlyticsPlugin mCrashlyticsPlugin = new CrashlyticsPlugin();
    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.app_bar_layout) AppBarLayout mAppBarLayout;
    @BindView(R.id.search_view) MaterialSearchView mSearchView;
    @BindView(R.id.fab_add_to_wishlist) FloatingActionButton mFabButton;
    @BindView(R.id.search_status_bar) View searchStatusBar;
    @BindView(R.id.iv_book_cover) ImageView mBookCover;
    @BindView(R.id.cv_overview) CardView mOverviewCard;
    @BindView(R.id.btn_edit_book) Button mEditButton;
    @BindViews({R.id.tv_author, R.id.tv_description, R.id.tv_genre}) List<TextView> mBookDescriptionsTextViews;
    @BindViews({R.id.tv_book_added_by, R.id.tv_book_owned_by}) List<TextView> mBookHoldersTextViews;

    public BookActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
        addPlugin(mAnalyticsPlugin);
        addPlugin(mCrashlyticsPlugin);
    }

    private final TiActivityPlugin<BookPresenter, BookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<BookPresenter>() {
                @NonNull
                @Override
                public BookPresenter providePresenter() {
                    String accountKey = App.readFromPreferences(BookActivity.this, "user_account_key", null);
                    String nickname = App.readFromPreferences(BookActivity.this, "user_login", null);
                    mBookPresenter = new BookPresenter(accountKey, nickname, getBookIntentExtra());
                    return mBookPresenter;
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        mSmallBang = SmallBang.attach2Window(this);
        Drawable navIcon = ContextCompat.getDrawable(this, R.drawable.ic_action_navigation_arrow_back_inverted);
        mToolbar = mToolbarPlugin.initCollapsingToolbar(navIcon, mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAddedBook) {
                    Intent intentToAddedBooks = new Intent(BookActivity.this, BookPanelActivity.class);
                    intentToAddedBooks.putExtra("page", 1);
                    startActivity(intentToAddedBooks);
                } else {
                    onBackPressed();
                }
            }
        });
        mBookPresenter = mPresenterPlugin.getPresenter();
        mBook = getBookIntentExtra();
        mCollapsingToolbarLayout.setTitle(mBook.getTitle());
        mDialog = new CardBookDialog(this);
        mAnalyticsPlugin.onBookEvent(mBook.getTitle(), mBook.getBookId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final String EMPTY_STRING = "";
        getMenuInflater().inflate(R.menu.menu_single_book, menu);

        searchStatusBar.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        MenuItem item = menu.findItem(R.id.action_search);

        mSearchView.setMenuItem(item);
        mSearchView.setOnQueryTextListener(this);
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
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, SearchBookActivity.class);
        intent.putExtra(getString(R.string.intent_put_search), query);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void setBookDescription(Book book) {
        mBookDescriptionsTextViews.get(0).setText(book.getAuthor());
        mBookDescriptionsTextViews.get(1).setText(book.getDesc());
        mBookDescriptionsTextViews.get(2).setText(book.getGenre());
    }

    @Override
    public void setBookCover(String coverUrl) {
        Glide.with(this)
                .load(WYKOPOLKA_IMG_HOST + coverUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(300, 400) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mBookCover.setImageBitmap(resource);
                    }
                });

    }

    @Override
    public void setWishStatusWilled() {
        mFabButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_36dp));
    }

    @Override
    public void setWishStatusNotWilled() {
        mFabButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_36dp));
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
    public void showEditButton() {
        if (mEditButton != null) {
            mEditButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    @OnClick(R.id.btn_edit_book)
    public void editBookDescription() {
        startEditBookActivity(mBook);
    }

    private void startEditBookActivity(Book book) {
        Intent intentBookActivity = new Intent(this, EditBookActivity.class);
        intentBookActivity.putExtra("book", book);

        if (BuildUtil.isMinApi21()) {
            String transitionName = getString(R.string.transition_book_cover);
            Pair<View, String> p1 = Pair.create((View) mBookCover, transitionName);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, p1);
            startActivity(intentBookActivity, transitionActivityOptions.toBundle());
        } else {
            startActivity(intentBookActivity);
        }
    }

    @Override
    @OnClick(R.id.btn_load_pdf)
    public void showBookCard() {
        mDialog.setBookData(mBook);
        mDialog.getDismissButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    @Override
    public void dismissBookCard() {
        mDialog.dismiss();
    }

    @Override
    @OnClick(R.id.fab_add_to_wishlist)
    public void addBookToWishList() {
        mBookPresenter.addBookToWishList();
        mSmallBang.bang(mFabButton);
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
    public void showSnackbarWishListError() {
        Snackbar.make(mFabButton, R.string.message_wish_list_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnackbarRemovedFromWishList() {
        Snackbar.make(mFabButton, R.string.message_removed_from_wish_list, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnackbarAddWishListSuccessful() {
        Snackbar snackbar = Snackbar.make(mFabButton, R.string.message_added_to_wish_list, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.action_go_to_wishlist, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookActivity.this, DashboardActivity.class);
                i.putExtra("page", WISH_LIST_PAGE);
                startActivity(i);
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
        isAddedBook = intent.getBooleanExtra("added_book", false);
        return book;
    }

    private void removeGradient() {
        View gradient = findViewById(R.id.gradient);
        if (gradient != null) gradient.setVisibility(View.GONE);
    }

}

