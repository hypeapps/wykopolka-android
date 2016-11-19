package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.BuildConfig;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.AddedBooksView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddedBooksPresenter extends TiPresenter<AddedBooksView> {

    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    RetrofitComponent mRetrofitComponent;

    private static final String WYKOPOLKA_SECRET = BuildConfig.WYKOPOLKA_SECRET;
    private String accountKey;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public AddedBooksPresenter(String accountKey) {
        this.accountKey = accountKey;
        Log.e("AddedBooksPresenter", this.accountKey);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        Log.e("base_URL", mRetrofit.baseUrl().toString());

    }

    private void loadData(String accountKey) {
        Log.e("acc_load_data", accountKey);
        String sign = HashUtil.md5(WYKOPOLKA_SECRET + accountKey);
        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);

        rxHelper.manageSubscription(
                wykopolkaApi.getUserAddedBooks(accountKey, sign)
                        .compose(RxTiPresenterUtils.<List<Book>>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<Book>>() {
                            @Override
                            public void onCompleted() {
                                Log.e("RxJava", " OnComplete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("onError", e.getMessage());
                            }

                            @Override
                            public void onNext(List<Book> books) {
                                Log.e("RxJava", "onNext: " + books.get(0).getCover());
                                getView().setBookData(books);
                            }
                        }));
    }

    public void initRefreshData() {
        loadData(accountKey);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        loadData(accountKey);
        Log.e("presenter", " onWakeUp");
    }

}