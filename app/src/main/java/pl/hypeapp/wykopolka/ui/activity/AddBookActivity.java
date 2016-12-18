package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.base.BaseUploadBookActivity;
import pl.hypeapp.wykopolka.presenter.AddBookPresenter;
import pl.hypeapp.wykopolka.view.AddBookView;

public class AddBookActivity extends BaseUploadBookActivity<AddBookPresenter, AddBookView> implements AddBookView {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.btn_commit_book) Button mCommitBookButton;
    private AddBookPresenter mAddBookPresenter;

    @NonNull
    @Override
    public AddBookPresenter providePresenter() {
        String accountKey = App.readFromPreferences(AddBookActivity.this, "user_account_key", null);
        mAddBookPresenter = new AddBookPresenter(accountKey);
        return mAddBookPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initToolbar(mToolbar);
        mCommitBookButton.setText(getString(R.string.title_activity_add_book));
        mAddBookPresenter = (AddBookPresenter) getPresenter();
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
        mAddBookPresenter.prepareBook();
    }

}