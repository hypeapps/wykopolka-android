package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.view.AddedBooksView;
import retrofit2.Retrofit;

public class AddedBooksPresenter extends TiPresenter<AddedBooksView> {

    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    RetrofitComponent mRetrofitComponent;

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        Log.e("base_URL", mRetrofit.baseUrl().toString());
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        Log.e("presenter", " onWakeUp");
    }
}