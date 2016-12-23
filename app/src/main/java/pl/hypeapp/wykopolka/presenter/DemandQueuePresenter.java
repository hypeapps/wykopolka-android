package pl.hypeapp.wykopolka.presenter;

import android.support.v4.util.Pair;
import android.util.Log;

import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterSubscriptionHandler;
import net.grandcentrix.thirtyinch.rx.RxTiPresenterUtils;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import pl.hypeapp.wykopolka.model.Book;
import pl.hypeapp.wykopolka.model.DemandQueue;
import pl.hypeapp.wykopolka.model.PendingUser;
import pl.hypeapp.wykopolka.network.api.WykopolkaApi;
import pl.hypeapp.wykopolka.network.retrofit.DaggerRetrofitComponent;
import pl.hypeapp.wykopolka.network.retrofit.RetrofitComponent;
import pl.hypeapp.wykopolka.util.HashUtil;
import pl.hypeapp.wykopolka.view.DemandQueueView;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DemandQueuePresenter extends TiPresenter<DemandQueueView> {
    @Inject
    @Named("wykopolkaApi")
    Retrofit mRetrofit;
    private RetrofitComponent mRetrofitComponent;
    private String mAccountKey;
    private WykopolkaApi mWykopolkaApi;
    private RxTiPresenterSubscriptionHandler rxHelper = new RxTiPresenterSubscriptionHandler(this);
    private DemandQueue mDemandQueue;

    public DemandQueuePresenter(String accountKey) {
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
        loadDemandQueue(mAccountKey);
    }

    @Override
    protected void onSleep() {
        super.onSleep();
        getView().dismissTransferDialog();
    }

    public void initRefreshData() {
        loadDemandQueue(mAccountKey);
    }

    public void onTransferBook(int position) {
        Pair<Book, PendingUser> bookPendingUserPair = new Pair<>(mDemandQueue.getBooks().get(position),
                mDemandQueue.getPendingUsers().get(position));
        getView().showTransferDialog(bookPendingUserPair);
    }

    public void onTransferConfirmed(Pair<Book, PendingUser> bookPendingUserPair) {
        getView().setDialogInLoading();
        String bookId = bookPendingUserPair.first.getBookId();
        int receiverId = bookPendingUserPair.second.getReceiverId();
        String sign = HashUtil.generateApiSign(mAccountKey, bookId, String.valueOf(receiverId));
        rxHelper.manageSubscription(mWykopolkaApi.transferBook(mAccountKey, bookId, receiverId, sign)
                .compose(RxTiPresenterUtils.<ResponseBody>deliverToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("eroro", e.getMessage());
                        onTransferError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.e("response", "ONNEXT");
                        onTransferSuccessful();
                    }
                })
        );
    }

    private void onTransferError() {
        getView().dismissTransferDialog();
        getView().dismissDialogLoading();
        getView().showTransferError();
    }

    private void onTransferSuccessful() {
        getView().dismissDialogLoading();
        getView().dismissTransferDialog();
        loadDemandQueue(mAccountKey);
    }

    private void loadDemandQueue(String accountKey) {
        getView().startLoadingAnimation();
        String sign = HashUtil.generateApiSign(accountKey);

        rxHelper.manageViewSubscription(mWykopolkaApi.getDemandQueue(accountKey, sign)
                .compose(RxTiPresenterUtils.<DemandQueue>deliverLatestToView(this))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DemandQueue>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDemandQueueErrorHandling();
                    }

                    @Override
                    public void onNext(DemandQueue demandQueue) {
                        Log.e("demandQueue", " " + demandQueue.books.get(0).getAuthor() + " " + demandQueue.pendingUsers.get(0).getAddress());
                        onDemandQueueLoadSuccessful(demandQueue);
                    }
                })
        );
    }

    private void onDemandQueueErrorHandling() {
        mDemandQueue.getBooks().clear();
        mDemandQueue.getPendingUsers().clear();
        getView().setBookData(mDemandQueue);
        getView().showError();
        getView().stopLoadingAnimation();
        getView().stopRefreshing();
    }

    private void onDemandQueueLoadSuccessful(DemandQueue demandQueue) {
        mDemandQueue = demandQueue;
        getView().setBookData(demandQueue);
        getView().hideError();
        getView().stopLoadingAnimation();
        getView().stopRefreshing();
    }

}