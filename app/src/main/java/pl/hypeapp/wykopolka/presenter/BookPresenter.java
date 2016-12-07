package pl.hypeapp.wykopolka.presenter;

import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import javax.inject.Inject;
import javax.inject.Named;

import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.model.WishBookStatus;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.BookView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookPresenter extends TiPresenter<BookView> {

    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private String mAccountKey;
    private String mBookId;
    private WykopolkaApi mWykopolkaApi;
    private Book mBook;
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    RetrofitComponent mRetrofitComponent;

    public BookPresenter(String accountKey, Book book) {
        this.mAccountKey = accountKey;
        this.mBookId = book.getBookId();
        this.mBook = book;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        loadWishBookStatus(mAccountKey, mBookId);
        setBookDescriptionToView(mBook);
        setBookHoldersToView(mBook);
    }


    private void loadWishBookStatus(final String accountKey, final String bookId) {
        String apiSign = HashUtil.generateApiSign(accountKey, bookId);

        rxHelper.manageViewSubscription(mWykopolkaApi.getWishBookStatus(accountKey, bookId, apiSign)
                .compose(RxTiPresenterUtils.<WishBookStatus>deliverLatestToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WishBookStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ONNEXT", "ERORR: " + e.getMessage());
                    }

                    @Override
                    public void onNext(WishBookStatus wishBookStatus) {
                        setWishBookStatusToView(wishBookStatus);
                    }
                })
        );
    }

    private void setBookDescriptionToView(Book book) {
        getView().setBookCover(book.getCover());
        getView().setBookDescription(book);
        getView().animateCardEnter();
    }

    private void setBookHoldersToView(Book book) {
        getView().setBookHolders(book.getAddedByLogin(), book.getOwnedByLogin());
    }

    private void setWishBookStatusToView(WishBookStatus wishBookStatus) {
        if (!wishBookStatus.getWishAllowed()) {
            getView().setWishIconDisabled();
        } else {
            if (wishBookStatus.getWishStatus()) {
                getView().setWishStatusWilled();
            } else {
                getView().setWishStatusNotWilled();
            }
        }
        getView().animateFabButtonEnter();
    }
}