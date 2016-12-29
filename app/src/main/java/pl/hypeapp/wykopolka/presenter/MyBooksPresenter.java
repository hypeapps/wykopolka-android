package pl.hypeapp.wykopolka.presenter;

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

public class MyBooksPresenter extends TiPresenter<BaseBookListView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private String mAccountKey;
    private RetrofitComponent mRetrofitComponent;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public MyBooksPresenter(String accountKey) {
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
        loadMyBooks(mAccountKey);
    }

    private void loadMyBooks(String accountKey) {
        getView().startLoadingAnimation();
        String apiSign = HashUtil.generateApiSign(accountKey);
        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);
        rxHelper.manageSubscription(
                wykopolkaApi.getMyBooks(accountKey, apiSign)
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
                                onErrorHandling();
                            }

                            @Override
                            public void onNext(List<Book> books) {
                                onNextHandling(books);
                            }
                        }));
    }

    private void onNextHandling(List<Book> books) {
        getView().setBookData(books);
        getView().hideError();
    }

    private void onErrorHandling() {
        getView().setBookData(Collections.<Book>emptyList());
        getView().showError();
    }

    public void initRefreshData() {
        loadMyBooks(mAccountKey);
    }
}