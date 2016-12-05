package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.BookListView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddedBooksPresenter extends TiPresenter<BookListView> {

    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private RetrofitComponent mRetrofitComponent;
    private String mAccountKey;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public AddedBooksPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

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
        loadAddedBooks(mAccountKey);
    }

    private void loadAddedBooks(String accountKey) {
        Log.e("acc_load_data", accountKey);
        String sign = HashUtil.generateApiSign(accountKey);
        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);

        rxHelper.manageSubscription(
                wykopolkaApi.getUserAddedBooks(accountKey, sign)
                        .compose(RxTiPresenterUtils.<List<Book>>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<Book>>() {
                            @Override
                            public void onCompleted() {
                                getView().stopLoadingAnimation();
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError();
                                getView().setBookData(Collections.<Book>emptyList());
                            }

                            @Override
                            public void onNext(List<Book> books) {
                                getView().setBookData(books);
                                getView().hideError();
                            }
                        }));
    }

    public void initRefreshData() {
        loadAddedBooks(mAccountKey);
    }

}