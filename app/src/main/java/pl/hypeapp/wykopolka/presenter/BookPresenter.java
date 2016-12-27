package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
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
    private static final int ADD_TO_WISH_LIST = 1;
    private static final int REMOVE_FROM_WISH_LIST = 0;
    private String mAccountKey;
    private String mBookId;
    private String mNickname;
    private WishBookStatus mWishBookStatus;
    private WykopolkaApi mWykopolkaApi;
    private Book mBook;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    RetrofitComponent mRetrofitComponent;

    public BookPresenter(String accountKey, String nickname, Book book) {
        this.mAccountKey = accountKey;
        this.mBookId = book.getBookId();
        this.mBook = book;
        this.mNickname = nickname;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRetrofitComponent = DaggerRetrofitComponent.builder().build();
        mRetrofitComponent.inject(this);
        mWykopolkaApi = mRetrofit.create(WykopolkaApi.class);
        loadWishBookStatus(mAccountKey, mBookId);
    }

    @Override
    protected void onWakeUp() {
        super.onWakeUp();
        setBookDescriptionToView(mBook);
        setBookHoldersToView(mBook);
        manageEditBookButton(mBook);
        if (mWishBookStatus != null) {
            setWishBookStatusToView(mWishBookStatus);
        }
    }

    @Override
    protected void onSleep() {
        super.onSleep();
        getView().dismissBookCard();
    }

    private void manageEditBookButton(Book book) {
        if (book.getAddedByLogin().equals(mNickname)) {
            getView().showEditButton();
        }
    }

    private void loadWishBookStatus(final String accountKey, final String bookId) {
        String apiSign = HashUtil.generateApiSign(accountKey, bookId);

        rxHelper.manageSubscription(mWykopolkaApi.getWishBookStatus(accountKey, bookId, apiSign)
                .compose(RxTiPresenterUtils.<WishBookStatus>deliverToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WishBookStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(WishBookStatus wishBookStatus) {
                        mWishBookStatus = wishBookStatus;
                        setWishBookStatusToView(mWishBookStatus);
                    }
                })
        );
    }

    public void addBookToWishList() {
        int wishOperation;
        boolean wishStatus = mWishBookStatus.getWishStatus();
        if (wishStatus) {
            wishOperation = REMOVE_FROM_WISH_LIST;
        } else {
            wishOperation = ADD_TO_WISH_LIST;
        }
        mWishBookStatus.setWishStatus(!wishStatus);
        setWishBookStatusWithSnackBar(mWishBookStatus);

        String apiSign = HashUtil.generateApiSign(mAccountKey, mBookId, String.valueOf(wishOperation));

        rxHelper.manageViewSubscription(mWykopolkaApi.manageWishList(mAccountKey, mBookId, wishOperation, apiSign)
                .compose(RxTiPresenterUtils.<ResponseBody>deliverToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mWishBookStatus.setWishAllowed(false);
                        setWishBookStatusWithSnackBar(mWishBookStatus);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
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
        getView().setBookHolders(book.getAddedByLoginFormatted(), book.getOwnedByLoginFormatted());
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

    private void setWishBookStatusWithSnackBar(WishBookStatus wishBookStatus) {
        if (!wishBookStatus.getWishAllowed()) {
            getView().setWishIconDisabled();
            getView().showSnackbarWishListError();
        } else {
            if (wishBookStatus.getWishStatus()) {
                getView().setWishStatusWilled();
                getView().showSnackbarAddWishListSuccessful();
            } else {
                getView().setWishStatusNotWilled();
                getView().showSnackbarRemovedFromWishList();
            }
        }
        getView().animateFabButtonEnter();
    }
}