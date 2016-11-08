package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.BooksRecyclerAdapter;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.NavigationDrawerActivityPlugin;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;
import pl.hypeapp.wykopolka.view.AddedBooksView;

public class AddedBooksActivity extends CompositeActivity implements AddedBooksView {
    BooksRecyclerAdapter mRecyclerAdapter;
    @BindView(R.id.book_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    static String[] mTitles;
    private AddedBooksPresenter addedBooksPresenter;

    private final NavigationDrawerActivityPlugin mNavigationDrawerPlugin = new NavigationDrawerActivityPlugin();
    private final TiActivityPlugin<AddedBooksPresenter, AddedBooksView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<AddedBooksPresenter>() {
                @NonNull
                @Override
                public AddedBooksPresenter providePresenter() {
                    String accountKey = App.readFromPreferences(AddedBooksActivity.this, "user_account_key", null);
                    addedBooksPresenter = new AddedBooksPresenter(accountKey);
                    return addedBooksPresenter;
                }
            });

    public AddedBooksActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mNavigationDrawerPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_books);
        ButterKnife.bind(this);

        toolbar = initToolbar();
        mNavigationDrawerPlugin.setNavigationDrawer(toolbar);
        initRecyclerAdapter();
    }

    private Toolbar initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;
    }

    private void initRecyclerAdapter() {
        mTitles = getResources().getStringArray(R.array.navigation_drawer_items);
        mRecyclerAdapter = new BooksRecyclerAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mRecyclerAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(scaleInAnimationAdapter);
    }

    @Override
    public void setDataToAdapter(List<Book> books) {
        mRecyclerAdapter.setData(books);
    }

}