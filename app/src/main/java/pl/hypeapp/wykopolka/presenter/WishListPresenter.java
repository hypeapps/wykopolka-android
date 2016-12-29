package pl.hypeapp.wykopolka.presenter;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.WishListView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WishListPresenter extends TiPresenter<WishListView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private RetrofitComponent mRetrofitComponent;
    private String mAccountKey;
    private WykopolkaApi mWykopolkaApi;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private List<Book> mBooks = Collections.emptyList();

    public WishListPresenter(String accountKey) {
        this.mAccountKey = accountKey;
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
        loadWishList(mAccountKey);
    }

    @Override
    protected void onSleep() {
        super.onSleep();
        getView().dismissConfirmDialog();
    }

    public void initRefreshData() {
        loadWishList(mAccountKey);
    }

    public void onConfirmBook(int position) {
        getView().showConfirmDialog(mBooks.get(position));
    }

    public void onBookClaimConfirmed(Book book, int quality) {
        getView().setDialogInLoading();
        String bookId = book.getBookId();
        int transferId = book.getTransferId();
        String sign = HashUtil.generateApiSign(mAccountKey, String.valueOf(quality), book.getBookId(), String.valueOf(transferId));

        rxHelper.manageSubscription(mWykopolkaApi.confirmClaimedBook(mAccountKey, bookId, transferId, quality, sign)
                .compose(RxTiPresenterUtils.<ResponseBody>deliverToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onConfirmationError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        onConfirmationSuccessful();
                    }
                })
        );
    }

    private void onConfirmationError() {
        getView().dismissConfirmDialog();
        getView().dismissDialogLoading();
        getView().showConfirmationError();
    }

    private void onConfirmationSuccessful() {
        getView().dismissDialogLoading();
        getView().dismissConfirmDialog();
        loadWishList(mAccountKey);
    }

    private void loadWishList(String accountKey) {
        getView().startLoadingAnimation();
        String sign = HashUtil.generateApiSign(accountKey);

        rxHelper.manageViewSubscription(mWykopolkaApi.getWishList(accountKey, sign)
                .compose(RxTiPresenterUtils.<List<Book>>deliverLatestToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onWishListErrorHandling();
                    }

                    @Override
                    public void onNext(List<Book> books) {
                        onWishListLoadSuccessful(books);
                    }
                })
        );
    }

    private void onWishListErrorHandling() {
        mBooks.clear();
        getView().setBookData(mBooks);
        getView().showError();
        getView().stopLoadingAnimation();
        getView().stopRefreshing();
    }

    private void onWishListLoadSuccessful(List<Book> books) {
        mBooks = books;
        getView().setBookData(books);
        getView().hideError();
        getView().stopLoadingAnimation();
        getView().stopRefreshing();
    }
}