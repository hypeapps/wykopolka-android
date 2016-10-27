package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.adapter.BooksRecyclerAdapter;
import pl.hypeapp.wykopolka.model.BookView;
import pl.hypeapp.wykopolka.plugin.NavigationDrawerActivityPlugin;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;
import pl.hypeapp.wykopolka.view.AddedBooksView;

public class AddedBooksActivity extends CompositeActivity implements AddedBooksView {
    BooksRecyclerAdapter mRecyclerAdapter;
    @BindView(R.id.book_list)
    RecyclerView mRecyclerView;
    static String[] mTitles;
    private final NavigationDrawerActivityPlugin mNavigationDrawerPlugin = new NavigationDrawerActivityPlugin();
    private final TiActivityPlugin<AddedBooksPresenter, AddedBooksView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<AddedBooksPresenter>() {
                @NonNull
                @Override
                public AddedBooksPresenter providePresenter() {
                    return new AddedBooksPresenter();
                }
            });

    public static List<BookView> getData() {
        List<BookView> dataSet = new ArrayList<>();

        String[] titles = mTitles;

        for (int i = 0; i < titles.length; i++) {
            BookView current = new BookView();
            current.title = "Awantury na tle powszechnego ciążenia";
            current.author = "Tomasz Lem";
            current.thumbnail = R.drawable.books;
            dataSet.add(current);
        }
        return dataSet;
    }

    public AddedBooksActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mNavigationDrawerPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_books);
        ButterKnife.bind(this);

        Toolbar toolbar = initToolbar();
        mNavigationDrawerPlugin.setNavigationDrawer(toolbar);
        initRecyclerAdapter();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;
    }

    private void initRecyclerAdapter() {
        mTitles = getResources().getStringArray(R.array.navigation_drawer_items);
        mRecyclerAdapter = new BooksRecyclerAdapter(this, getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mRecyclerAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(scaleInAnimationAdapter);
    }
}