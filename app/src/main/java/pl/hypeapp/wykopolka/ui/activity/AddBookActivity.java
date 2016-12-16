package pl.hypeapp.wykopolka.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.plugin.ToolbarActivityPlugin;
import pl.hypeapp.wykopolka.presenter.AddBookPresenter;
import pl.hypeapp.wykopolka.ui.base.BaseCommitBookActivity;
import pl.hypeapp.wykopolka.view.AddBookView;

public class AddBookActivity extends BaseCommitBookActivity implements AddBookView {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.btn_commit_book) Button mCommitBookButton;
    private AddBookPresenter mAddBookPresenter;
    private Bitmap mBookCover;
    private Book mBook;

    private final ToolbarActivityPlugin mToolbarPlugin = new ToolbarActivityPlugin();

    private final TiActivityPlugin<AddBookPresenter, AddBookView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<AddBookPresenter>() {
                @NonNull
                @Override
                public AddBookPresenter providePresenter() {
                    String accountKey = App.readFromPreferences(AddBookActivity.this, "user_account_key", null);
                    mAddBookPresenter = new AddBookPresenter(accountKey);
                    return mAddBookPresenter;
                }
            });

    public AddBookActivity() {
        addPlugin(mPresenterPlugin);
        addPlugin(mToolbarPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_commit_book);
        ButterKnife.bind(this);
        initToolbar(mToolbar);
        View view = findViewById(R.id.commit_book_layout);
        initViews(view);
        mCommitBookButton.setText(getString(R.string.title_activity_add_book));
        mAddBookPresenter = mPresenterPlugin.getPresenter();
//        Bitmap cover = BitmapFactory.decodeResource(getResources(), R.drawable.books);
//        Book book = new Book();
//        mAddBookPresenter.uploadBook(cover, book);
    }

    public Toolbar initToolbar(Toolbar toolbar) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_navigation_arrow_back_inverted));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return mToolbar;
    }

    @Override
    @OnClick(R.id.btn_commit_book)
    public void addBook() {
        mAddBookPresenter.uploadBook();
    }

}