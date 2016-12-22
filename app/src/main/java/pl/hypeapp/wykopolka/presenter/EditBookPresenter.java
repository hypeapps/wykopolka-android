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
import pl.hypeapp.wykopolka.view.EditBookView;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditBookPresenter extends BaseUploadBookPresenter<EditBookView> {
    private static int BOOK_INDEX = 0;
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private String mAccountkey;
    private WykopolkaApi mWykopolkaApi;
    private Book book;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);

    public EditBookPresenter(String accountKey, Book book) {
        this.mAccountkey = accountKey;
        this.book = book;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RetrofitComponent mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        setBookInfo();
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        if (mCoverPhoto != null) {
            getView().setCoverBitmap(mCoverPhoto);
        } else {
            getView().setCover(book.getCover());
        }
    }

    private Observable<Book> setBookObservable(Book book) {
        return Observable.just(book);
    }

    private void setBookInfo() {
        rxHelper.manageSubscription(setBookObservable(book)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxTiPresenterUtils.<Book>deliverToView(this))
                .subscribe(new Subscriber<Book>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Book book) {
                        if (getView() != null) {
                            getView().setCover(book.getCover());
                            getView().setTitle(book.getTitle());
                            getView().setAuthor(book.getAuthor());
                            getView().setGenre(book.getGenre());
                            getView().setDescription(book.getDesc());
                            getView().setQuality(book.getQuality());
                            getView().setIsbn(book.getIsbn());
                            getView().setRating(book.getRating());
                        }
                    }
                })
        );
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
                                Book book = createEditedBook(EditBookPresenter.this.book, s);
                                uploadBookCall(book);
                            }
                        }));
            } else {
                Book book = createEditedBook(this.book, this.book.getCover());
                uploadBookCall(book);
            }
        }
    }

    private void uploadBookCall(Book book) {
        String bookJson = createBookJson(book);
        if (bookJson != null) {
            String sign = HashUtil.generateApiSign(mAccountkey, bookJson);
            mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
            rxHelper.manageSubscription(mWykopolkaApi.editBook(mAccountkey, bookJson, sign)
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
                        public void onNext(List<Book> books) {
                            stopLoading();
                            getView().uploadingBookSuccessful(books.get(BOOK_INDEX));
                        }
                    }));

        }
    }
}