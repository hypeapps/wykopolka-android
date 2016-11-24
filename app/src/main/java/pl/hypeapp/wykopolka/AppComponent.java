package pl.hypeapp.wykopolka;

import javax.inject.Singleton;

import dagger.Component;
import pl.hypeapp.wykopolka.network.retrofit.WykopolkaRetrofitModule;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;

@Singleton
@Component(modules = {AppModule.class, WykopolkaRetrofitModule.class})
public interface AppComponent {

    void inject (AddedBooksPresenter addedBooksPresenter);
}
