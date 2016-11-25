package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.jpardogo.android.googleprogressbar.library.GoogleMusicDicesDrawable;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.NavigationDrawerActivityPlugin;
import pl.hypeapp.wykopolka.presenter.RandomBookPresenter;
import pl.hypeapp.wykopolka.view.RandomBookView;

public class RandomBookActivity extends CompositeActivity implements RandomBookView {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.google_progress) ProgressBar mRandomizeProgress;

    private final NavigationDrawerActivityPlugin mNavigationDrawerPlugin = new NavigationDrawerActivityPlugin();

    private final TiActivityPlugin<RandomBookPresenter, RandomBookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<RandomBookPresenter>() {
                @NonNull
                @Override
                public RandomBookPresenter providePresenter() {
                    return new RandomBookPresenter();
                }
            });

    public RandomBookActivity() {
        addPlugin(mNavigationDrawerPlugin);
        addPlugin(mPresenterPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_book);
        ButterKnife.bind(this);

        mToolbar = initToolbar();
        mNavigationDrawerPlugin.setNavigationDrawer(mToolbar);
        mRandomizeProgress.setIndeterminateDrawable(new GoogleMusicDicesDrawable.Builder().build());
        mRandomizeProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRandomBook(List<Book> book) {

    }

    @Override
    public void nextRandomBook() {

    }

    private Toolbar initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;
    }

}