package pl.hypeapp.wykopolka.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import pl.hypeapp.wykopolka.R;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;
import pl.hypeapp.wykopolka.ui.fragment.NavigationDrawerFragment;
import pl.hypeapp.wykopolka.view.AddedBooksView;

public class AddedBooksActivity extends CompositeActivity implements AddedBooksView {
    private final TiActivityPlugin<AddedBooksPresenter, AddedBooksView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<AddedBooksPresenter>() {
                @NonNull
                @Override
                public AddedBooksPresenter providePresenter() {
                    return new AddedBooksPresenter();
                }
            });

    public AddedBooksActivity() {
        addPlugin(mPresenterPlugin);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_books);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Wykopółka");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
//                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerFragment.create(drawerLayout, toolbar, R.id.fragment_navigation_drawer);
    }
}