package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.base.BaseUploadBookPresenter;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.AddBookView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddBookPresenter extends BaseUploadBookPresenter<AddBookView> {
    private static int BOOK_INDEX = 0;
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private String mAccountkey;
    private WykopolkaApi mWykopolkaApi;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public AddBookPresenter(String accountKey) {
        mAccountkey = accountKey;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RetrofitComponent mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        if (mCoverPhoto != null) {
            getView().setCoverBitmap(mCoverPhoto);
        }
    }

    public void prepareBook() {
        if (isInputsCompatible()) {
            startLoading();
            if (mCoverPhoto != null) {
                rxHelper.manageSubscription(decodeCoverAsync()
                        .compose(RxTiPresenterUtils.<String>deliverLatestToView(this))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                stopLoading();
                                getView().showUploadError();
                            }

                            @Override
                            public void onNext(String s) {
                                Book book = createBookToAdd(s);
                                uploadBookCall(book);
                            }
                        }));
            } else {
                Book book = createBookToAdd(null);
                uploadBookCall(book);
            }
        }
    }

    private void uploadBookCall(Book book) {
        String bookJson = createBookJson(book);
        if (bookJson != null) {
            String sign = HashUtil.generateApiSign(mAccountkey, bookJson);
            mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);

            rxHelper.manageSubscription(mWykopolkaApi.uploadBook(mAccountkey, bookJson, sign)
                    .compose(RxTiPresenterUtils.<List<Book>>deliverLatestToView(this))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Book>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            stopLoading();
                            getView().showUploadError();
                        }

                        @Override
                        public void onNext(List<Book> book) {
                            stopLoading();
                            getView().uploadingBookSuccessful(book.get(BOOK_INDEX));
                        }
                    })
            );
        }
    }
}