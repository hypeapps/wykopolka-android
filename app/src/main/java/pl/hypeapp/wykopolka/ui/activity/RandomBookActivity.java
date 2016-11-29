package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
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
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.RandomBookPresenter;
import pl.hypeapp.wykopolka.view.RandomBookView;

public class RandomBookActivity extends CompositeActivity implements RandomBookView {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.google_progress) ProgressBar mRandomizeProgress;

    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();

    private final TiActivityPlugin<RandomBookPresenter, RandomBookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<RandomBookPresenter>() {
                @NonNull
                @Override
                public RandomBookPresenter providePresenter() {
                    return new RandomBookPresenter();
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
//        mRandomizeProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRandomBook(List<Book> book) {

    }

    @Override
    public void nextRandomBook() {

    }
}