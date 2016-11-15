package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
    private AddedBooksPresenter addedBooksPresenter;
    private List<Book> books;

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

    @Override
    public void setBookData(List<Book> books) {
        this.books = books;
        mRecyclerAdapter.setData(this.books);
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
        mRecyclerAdapter = new BooksRecyclerAdapter(this, new onBookClickListener());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mRecyclerAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(scaleInAnimationAdapter);
    }

    public class onBookClickListener {

        public void showBookActivity(int position) {
            String bookId = books.get(position).getBookId();
            String bookTitle = books.get(position).getTitle();
            Log.e("BOOK_ID", " " + bookId);
            Intent intentBookActivity = new Intent(AddedBooksActivity.this, BookActivity.class);
            intentBookActivity.putExtra("BOOK_ID", bookId);
            intentBookActivity.putExtra("BOOK_TITLE", bookTitle);
            startActivity(intentBookActivity);
        }
    }
}