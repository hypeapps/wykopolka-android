package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.base.BaseBookListView;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SelectedBooksPresenter extends TiPresenter<BaseBookListView> {
    private static final String BOOKS_AMOUNT = "20";
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private RetrofitComponent mRetrofitComponent;
    private String mAccountKey;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    
    public SelectedBooksPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        loadSelectedBooks(mAccountKey, BOOKS_AMOUNT);
    }

    private void loadSelectedBooks(String accountKey, String amount) {
        String sign = HashUtil.generateApiSign(accountKey, amount);
        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);

        rxHelper.manageSubscription(
                wykopolkaApi.getSelectedBooks(accountKey, amount, sign)
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
                                Log.e("err", "onError: " + e.getMessage());
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
        loadSelectedBooks(mAccountKey, BOOKS_AMOUNT);
    }

}