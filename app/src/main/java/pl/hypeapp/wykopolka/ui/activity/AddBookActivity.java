package pl.hypeapp.wykopolka.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import pl.hypeapp.wykopolka.App;
import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.presenter.AddBookPresenter;
import pl.hypeapp.wykopolka.view.AddBookView;

public class AddBookActivity extends CompositeActivity implements AddBookView {
    private AddBookPresenter mAddBookPresenter;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        mAddBookPresenter = mPresenterPlugin.getPresenter();

        Bitmap cover = BitmapFactory.decodeResource(getResources(), R.drawable.books);
        Book book = new Book();
        mAddBookPresenter.uploadBook(cover, book);
    }
}