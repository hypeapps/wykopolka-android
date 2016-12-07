package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jpardogo.android.googleprogressbar.library.GoogleMusicDicesDrawable;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.RandomBookPresenter;
import pl.hypeapp.wykopolka.util.BuildUtil;
import pl.hypeapp.wykopolka.view.RandomBookView;

public class RandomBookActivity extends CompositeActivity implements RandomBookView {
    private RandomBookPresenter mRandomBookPresenter;
    private Book mBook;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.google_progress) ProgressBar mRandomizeProgress;
    @BindView(R.id.tv_book_title) TextView mBookTitle;
    @BindView(R.id.tv_book_author) TextView mBookAuthor;
    @BindView(R.id.iv_book_cover) ImageView mBookCover;
    @BindView(R.id.layout_random_animation) View mRandomizeAnimationLayout;
    @BindView(R.id.layout_random_book) View mRandomBookLayout;
    @BindView(R.id.error_view) View errorView;

    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();

    private final TiActivityPlugin<RandomBookPresenter, RandomBookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<RandomBookPresenter>() {
                @NonNull
                @Override
                public RandomBookPresenter providePresenter() {
                    String accountKey = App.readFromPreferences(RandomBookActivity.this, "user_account_key", null);
                    mRandomBookPresenter = new RandomBookPresenter(accountKey);
                    return mRandomBookPresenter;
                }
            });

    public RandomBookActivity() {
        addPlugin(mToolbarPlugin);
        addPlugin(mPresenterPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_book);
        ButterKnife.bind(this);
        mToolbar = mToolbarPlugin.initToolbar(mToolbar);
        mToolbarPlugin.setNavigationDrawer(mToolbar);
        mRandomizeProgress.setIndeterminateDrawable(new GoogleMusicDicesDrawable.Builder().build());
        mRandomBookPresenter = mPresenterPlugin.getPresenter();
    }

    @Override
    public void showRandomBook(Book randomBook) {
        this.mBook = randomBook;
        if (mRandomBookLayout != null) {
            mBookTitle.setText(randomBook.getTitle());
            mBookAuthor.setText(randomBook.getAuthor());
            Glide.with(this)
                    .load(App.WYKOPOLKA_IMG_HOST + randomBook.getCover())
                    .into(mBookCover);
            mRandomBookLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRandomBookWithAnimation(Book book) {
        showRandomBook(book);
        startZoomInAnimation();
    }

    @Override
    public void hideRandomBook() {
        if (mRandomBookLayout != null) {
            mRandomBookLayout.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    @OnClick({R.id.btn_random_next_book, R.id.btn_error_retry})
    public void nextRandomBook() {
        mRandomBookPresenter.initLoadAgain();
    }

    @Override
    public void showRandomizeAnimation() {
        if (mRandomizeAnimationLayout != null) {
            mRandomizeAnimationLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideRandomizeAnimation() {
        if (mRandomizeAnimationLayout != null) {
            mRandomizeAnimationLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        if (errorView != null) {
            errorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideError() {
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
    }

    private void startZoomInAnimation() {
        YoYo.with(Techniques.ZoomIn).playOn(mRandomBookLayout);
    }

    @OnClick(R.id.card_view_random_book)
    public void startBookActivity() {
        Intent intentBookActivity = new Intent(this, BookActivity.class);
        intentBookActivity.putExtra("book", mBook);

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
}