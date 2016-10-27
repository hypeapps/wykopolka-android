package pl.hypeapp.wykopolka.network.retrofit;

import javax.inject.Singleton;

import dagger.Component;
import pl.hypeapp.wykopolka.presenter.AddedBooksPresenter;
import pl.hypeapp.wykopolka.presenter.SignInPresenter;

@Singleton
@Component(modules = { WykopolkaRetrofitModule.class })
public interface RetrofitComponent {

    void inject(AddedBooksPresenter tiPresenter);

    void inject(SignInPresenter tiPresenter);
}
