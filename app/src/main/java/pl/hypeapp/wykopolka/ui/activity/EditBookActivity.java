package pl.hypeapp.wykopolka.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.base.BaseUploadBookActivity;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.presenter.EditBookPresenter;
import pl.hypeapp.wykopolka.view.EditBookView;

public class EditBookActivity extends BaseUploadBookActivity<EditBookPresenter, EditBookView> implements EditBookView {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.btn_commit_book) Button mCommitBookButton;
    private EditBookPresenter mEditBookPresenter;
    private Crashlytics mCrashlytics;

    @NonNull
    @Override
    public EditBookPresenter providePresenter() {
        String accountKey = App.readFromPreferences(EditBookActivity.this, "user_account_key", null);
        mEditBookPresenter = new EditBookPresenter(accountKey, getBookIntentExtra());
        return mEditBookPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mCrashlytics = new Crashlytics();
        Fabric.with(this, mCrashlytics);
        super.onCreate(savedInstanceState);
        initToolbar(mToolbar);
        mCommitBookButton.setText(getString(R.string.title_activity_edit_book));
        mEditBookPresenter = (EditBookPresenter) getPresenter();
    }

    private Book getBookIntentExtra() {
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        return book;
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
    public void uploadEditedBook() {
        mEditBookPresenter.prepareBook();
    }
}