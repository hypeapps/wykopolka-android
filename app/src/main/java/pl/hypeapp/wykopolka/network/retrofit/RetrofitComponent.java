package pl.hypeapp.wykopolka.network.retrofit;

import javax.inject.Singleton;

import dagger.Component;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;
import pl.hypeapp.wykopolka.presenter.BookPanelPresenter;
import pl.hypeapp.wykopolka.presenter.BookPresenter;
import pl.hypeapp.wykopolka.presenter.MyBooksPresenter;
import pl.hypeapp.wykopolka.presenter.RandomBookPresenter;
import pl.hypeapp.wykopolka.presenter.SignInPresenter;

@Singleton
@Component(modules = { WykopolkaRetrofitModule.class })
public interface RetrofitComponent {

    void inject(BookPanelPresenter tiPresenter);

    void inject(SignInPresenter tiPresenter);

    void inject(BookPresenter tiPresenter);

    void inject(AddedBooksPresenter tiPresenter);

    void inject(MyBooksPresenter tiPresenter);

    void inject(RandomBookPresenter tiPresenter);

}
