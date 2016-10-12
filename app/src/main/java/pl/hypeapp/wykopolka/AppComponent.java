package pl.hypeapp.wykopolka;

import javax.inject.Singleton;

import dagger.Component;
import pl.hypeapp.wykopolka.network.retrofit.WykopolkaRetrofitModule;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;
import pl.hypeapp.wykopolka.ui.activity.AddedBooksActivity;

@Singleton
@Component(modules = {AppModule.class, WykopolkaRetrofitModule.class})
public interface AppComponent {

    void inject(AddedBooksActivity addedBooksActivity);

    void inject (AddedBooksPresenter addedBooksPresenter);
}
