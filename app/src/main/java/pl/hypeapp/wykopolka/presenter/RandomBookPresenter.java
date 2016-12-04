package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.RandomBookView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RandomBookPresenter extends TiPresenter<RandomBookView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private String mAccountKey;
    private Book mBook;
    private RetrofitComponent mRetrofitComponent;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private boolean isAfterError = false;

    public RandomBookPresenter(String accountKey) {
        this.mAccountKey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        loadRandomBook(mAccountKey);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        if (isAfterError) {
            loadRandomBook(mAccountKey);
        } else {
            setRandomBook(mBook);
        }
    }

    private void loadRandomBook(String accountKey) {
        String sign = HashUtil.generateApiSign(accountKey);
        WykopolkaApi wykopolkaApi = mRetrofit.create(WykopolkaApi.class);

        rxHelper.manageSubscription(
                wykopolkaApi.getRandomBook(accountKey, sign)
                        .compose(RxTiPresenterUtils.<Book>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .delay(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Book>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                getView().showError();
                                getView().hideRandomizeAnimation();
                                isAfterError = true;
                            }

                            @Override
                            public void onNext(Book book) {
                                mBook = book;
                                isAfterError = false;
                                setRandomBookWithAnimation(book);
                            }
                        }));

    }

    private void setRandomBook(Book book) {
        if (book != null) {
            getView().hideError();
            getView().hideRandomizeAnimation();
            getView().showRandomBook(book);
        }
    }

    private void setRandomBookWithAnimation(Book book) {
        if (book != null) {
            getView().hideError();
            getView().hideRandomizeAnimation();
            getView().showRandomBookWithAnimation(book);
        }
    }

    public void initLoadAgain() {
        getView().hideRandomBook();
        getView().hideError();
        getView().showRandomizeAnimation();
        loadRandomBook(mAccountKey);
    }
}